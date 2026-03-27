package com.tickethub.indentity.dto;

import com.tickethub.indentity.entity.enums.DiscountType;
import com.tickethub.indentity.entity.enums.PromoApplyOn;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDtos {
    public record CreateVoucherRequest(
            @NotBlank String code,
            @NotNull DiscountType discountType,
            @NotNull @DecimalMin("0") BigDecimal discountValue,
            @DecimalMin("0") BigDecimal minOrderValue,
            Integer usageLimit,
            @NotNull LocalDateTime validFrom,
            @NotNull LocalDateTime validUntil,
            @NotNull PromoApplyOn applyOn,
            Boolean isActive
    ) {
    }

    public record UseVoucherRequest(
            @NotNull UUID orderId,
            @NotNull UUID userId,
            @NotNull @DecimalMin("0") BigDecimal discountApplied
    ) {
    }

    public record VoucherResponse(
            UUID id,
            UUID organizerId,
            String code,
            DiscountType discountType,
            BigDecimal discountValue,
            BigDecimal minOrderValue,
            Integer usageLimit,
            Integer usedCount,
            LocalDateTime validFrom,
            LocalDateTime validUntil,
            PromoApplyOn applyOn,
            boolean isActive
    ) {
    }
}
