package com.tickethub.indentity.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlatformConfigDtos {
    public record ConfigResponse(
            UUID id,
            String configKey,
            String configValue,
            String description,
            UUID updatedBy,
            LocalDateTime updatedAt
    ) {
    }

    public record UpdateConfigRequest(
            @NotBlank String configValue,
            String description
    ) {
    }
}
