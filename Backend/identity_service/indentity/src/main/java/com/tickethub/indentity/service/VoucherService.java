package com.tickethub.indentity.service;

import com.tickethub.indentity.dto.VoucherDtos;
import com.tickethub.indentity.entity.User;
import com.tickethub.indentity.entity.Vouchers;
import com.tickethub.indentity.entity.VouchersUsages;
import com.tickethub.indentity.events.VoucherUpdatedEvent;
import com.tickethub.indentity.repository.UserRepository;
import com.tickethub.indentity.repository.VoucherUsageRepository;
import com.tickethub.indentity.repository.VouchersRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VoucherService {
    private final VouchersRepository vouchersRepository;
    private final VoucherUsageRepository voucherUsageRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final LoggingService loggingService;

    public VoucherService(
            VouchersRepository vouchersRepository,
            VoucherUsageRepository voucherUsageRepository,
            UserRepository userRepository,
            ApplicationEventPublisher eventPublisher,
            LoggingService loggingService
    ) {
        this.vouchersRepository = vouchersRepository;
        this.voucherUsageRepository = voucherUsageRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
        this.loggingService = loggingService;
    }

    public VoucherDtos.VoucherResponse createVoucher(String currentEmail, VoucherDtos.CreateVoucherRequest request, String ipAddress) {
        User organizer = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (vouchersRepository.findByCode(request.code()).isPresent()) {
            throw new RuntimeException("Voucher code already exists");
        }

        Vouchers voucher = new Vouchers();
        voucher.setOrganizer(organizer);
        voucher.setCode(request.code());
        voucher.setDiscountType(request.discountType());
        voucher.setDiscountValue(request.discountValue());
        voucher.setMinOrderValue(request.minOrderValue());
        voucher.setUsageLimit(request.usageLimit());
        voucher.setValidFrom(request.validFrom());
        voucher.setValidUntil(request.validUntil());
        voucher.setApplyOn(request.applyOn());
        voucher.setActive(request.isActive() == null || request.isActive());
        voucher.setUsedCount(0);

        Vouchers saved = vouchersRepository.save(voucher);
        eventPublisher.publishEvent(new VoucherUpdatedEvent(saved.getId()));
        loggingService.logSystem(organizer, "CREATE_VOUCHER", "VOUCHER", saved.getId(), "{\"status\":\"created\"}", ipAddress);
        return toResponse(saved);
    }

    public VoucherDtos.VoucherResponse getByCode(String code) {
        Vouchers voucher = vouchersRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Voucher not found"));
        return toResponse(voucher);
    }

    public void useVoucher(UUID voucherId, VoucherDtos.UseVoucherRequest request, String currentEmail, String ipAddress) {
        Vouchers voucher = vouchersRepository.findById(voucherId).orElseThrow(() -> new RuntimeException("Voucher not found"));
        User actor = userRepository.findByEmail(currentEmail).orElseThrow(() -> new RuntimeException("User not found"));

        if (!voucher.isActive() || LocalDateTime.now().isBefore(voucher.getValidFrom()) || LocalDateTime.now().isAfter(voucher.getValidUntil())) {
            throw new RuntimeException("Voucher is not valid at this time");
        }
        if (voucher.getUsageLimit() != null && voucher.getUsedCount() >= voucher.getUsageLimit()) {
            throw new RuntimeException("Voucher usage limit exceeded");
        }

        VouchersUsages usage = new VouchersUsages();
        usage.setVoucher(voucher);
        usage.setOrderId(request.orderId());
        usage.setUserId(request.userId());
        usage.setDiscountApplied(request.discountApplied());
        voucherUsageRepository.save(usage);

        voucher.setUsedCount(voucher.getUsedCount() + 1);
        vouchersRepository.save(voucher);
        eventPublisher.publishEvent(new VoucherUpdatedEvent(voucher.getId()));
        loggingService.logSystem(actor, "USE_VOUCHER", "VOUCHER", voucher.getId(), "{\"status\":\"used\"}", ipAddress);
    }

    private VoucherDtos.VoucherResponse toResponse(Vouchers voucher) {
        return new VoucherDtos.VoucherResponse(
                voucher.getId(),
                voucher.getOrganizer() == null ? null : voucher.getOrganizer().getId(),
                voucher.getCode(),
                voucher.getDiscountType(),
                voucher.getDiscountValue(),
                voucher.getMinOrderValue(),
                voucher.getUsageLimit(),
                voucher.getUsedCount(),
                voucher.getValidFrom(),
                voucher.getValidUntil(),
                voucher.getApplyOn(),
                voucher.isActive()
        );
    }
}
