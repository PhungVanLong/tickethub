package com.tickethub.indentity.controller;

import com.tickethub.indentity.dto.PlatformConfigDtos;
import com.tickethub.indentity.service.PlatformConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform/config")
@Tag(name = "Platform Config")
public class PlatformConfigController {
    private final PlatformConfigService platformConfigService;

    public PlatformConfigController(PlatformConfigService platformConfigService) {
        this.platformConfigService = platformConfigService;
    }

    @GetMapping
    @Operation(summary = "Get platform configuration")
    public ResponseEntity<List<PlatformConfigDtos.ConfigResponse>> getConfigs() {
        return ResponseEntity.ok(platformConfigService.getAllConfigs());
    }

    @PutMapping("/{key}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update platform configuration")
    public ResponseEntity<PlatformConfigDtos.ConfigResponse> updateConfig(
            @PathVariable String key,
            @Valid @RequestBody PlatformConfigDtos.UpdateConfigRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.ok(platformConfigService.updateConfig(key, request, authentication.getName(), httpRequest.getRemoteAddr()));
    }
}
