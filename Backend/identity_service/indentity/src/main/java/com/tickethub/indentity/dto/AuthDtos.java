package com.tickethub.indentity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {

    public record RegisterRequest(
            @Email @NotBlank String email,
            @NotBlank @Size(min = 8) String password,
            @NotBlank String fullName,
            String phone,
            String avatarUrl,
            String captchaToken,
            boolean captchaPassed,
            float captchaScore
    ) {
    }

    public record LoginRequest(
            @Email @NotBlank String email,
            @NotBlank String password,
            String captchaToken,
            boolean captchaPassed,
            float captchaScore
    ) {
    }

    public record RefreshTokenRequest(@NotBlank String refreshToken) {
    }

    public record LogoutRequest(@NotBlank String refreshToken) {
    }
}
