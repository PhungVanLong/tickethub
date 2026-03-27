package com.tickethub.indentity.dto;

import com.tickethub.indentity.entity.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDtos {
    public record UserResponse(
            UUID id,
            String email,
            String fullName,
            String phone,
            String avatarUrl,
            UserRole role,
            boolean isVerified,
            boolean isActive,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }

    public record UpdateMeRequest(
            @NotBlank String fullName,
            String phone,
            String avatarUrl
    ) {
    }
}
