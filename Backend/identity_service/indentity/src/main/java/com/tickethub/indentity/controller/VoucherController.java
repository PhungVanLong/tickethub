package com.tickethub.indentity.controller;

import com.tickethub.indentity.dto.VoucherDtos;
import com.tickethub.indentity.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vouchers")
@Tag(name = "Vouchers")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    @Operation(summary = "Create voucher")
    public ResponseEntity<VoucherDtos.VoucherResponse> createVoucher(
            Authentication authentication,
            @Valid @RequestBody VoucherDtos.CreateVoucherRequest request,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.ok(voucherService.createVoucher(authentication.getName(), request, httpRequest.getRemoteAddr()));
    }

    @GetMapping("/{code}")
    @Operation(summary = "Check voucher by code")
    public ResponseEntity<VoucherDtos.VoucherResponse> getVoucherByCode(@PathVariable String code) {
        return ResponseEntity.ok(voucherService.getByCode(code));
    }

    @PostMapping("/{id}/use")
    @Operation(summary = "Record voucher usage")
    public ResponseEntity<Void> useVoucher(
            @PathVariable UUID id,
            @Valid @RequestBody VoucherDtos.UseVoucherRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest
    ) {
        voucherService.useVoucher(id, request, authentication.getName(), httpRequest.getRemoteAddr());
        return ResponseEntity.ok().build();
    }
}
