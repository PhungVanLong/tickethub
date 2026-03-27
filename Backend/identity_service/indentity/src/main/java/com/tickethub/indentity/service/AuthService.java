package com.tickethub.indentity.service;

import com.tickethub.indentity.config.JwtUtils;
import com.tickethub.indentity.config.TokenBlacklistService;
import com.tickethub.indentity.dto.AuthDtos;
import com.tickethub.indentity.dto.TokenResponse;
import com.tickethub.indentity.entity.User;
import com.tickethub.indentity.entity.enums.UserRole;
import com.tickethub.indentity.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final LoggingService loggingService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            LoggingService loggingService,
            TokenBlacklistService tokenBlacklistService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.loggingService = loggingService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public TokenResponse register(AuthDtos.RegisterRequest request, String ipAddress) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setAvatarUrl(request.avatarUrl());
        if (request.fullName().equals("admin")) {
            user.setRole(UserRole.ROLE_ADMIN);
        } else {
            user.setRole(UserRole.ROLE_CUSTOMER);
        }

        user.setVerified(false);
        user.setActive(true);
        User saved = userRepository.save(user);

        loggingService.logCaptcha(saved, "REGISTER", request.captchaToken(), request.captchaPassed(), request.captchaScore());
        loggingService.logSystem(saved, "REGISTER", "USER", saved.getId(), "{\"status\":\"created\"}", ipAddress);
        return mapToken(saved);
    }

    public TokenResponse login(AuthDtos.LoginRequest request, String ipAddress) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("User not found"));
        loggingService.logCaptcha(user, "LOGIN", request.captchaToken(), request.captchaPassed(), request.captchaScore());
        loggingService.logSystem(user, "LOGIN", "USER", user.getId(), "{\"status\":\"success\"}", ipAddress);
        return mapToken(user);
    }

    public TokenResponse refresh(AuthDtos.RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        if (tokenBlacklistService.isBlacklisted(refreshToken) || !jwtUtils.isTokenValid(refreshToken, "refresh")) {
            throw new RuntimeException("Invalid refresh token");
        }
        String email = jwtUtils.extractSubject(refreshToken);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToken(user);
    }

    public void logout(String accessToken, AuthDtos.LogoutRequest request, String ipAddress) {
        tokenBlacklistService.blacklist(accessToken);
        tokenBlacklistService.blacklist(request.refreshToken());
        try {
            String email = jwtUtils.extractSubject(accessToken);
            User user = userRepository.findByEmail(email).orElseThrow();
            loggingService.logSystem(user, "LOGOUT", "USER", user.getId(), "{\"status\":\"success\"}", ipAddress);
        } catch (Exception ignored) {
        }
    }

    private TokenResponse mapToken(User user) {
        Map<String, Object> tokenResponse = jwtUtils.asTokenResponse(user.getId().toString(), user.getEmail(), user.getRole().name());
        return new TokenResponse(
                tokenResponse.get("accessToken").toString(),
                tokenResponse.get("refreshToken").toString(),
                tokenResponse.get("tokenType").toString(),
                (Long) tokenResponse.get("expiresIn")
        );
    }
}
