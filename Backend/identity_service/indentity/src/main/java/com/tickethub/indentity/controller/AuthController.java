package com.tickethub.indentity.controller;

import com.tickethub.indentity.dto.AuthDtos;
import com.tickethub.indentity.dto.TokenResponse;
import com.tickethub.indentity.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new account")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody AuthDtos.RegisterRequest request, HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.register(request, httpRequest.getRemoteAddr()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and return JWT tokens")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody AuthDtos.LoginRequest request, HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.login(request, httpRequest.getRemoteAddr()));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody AuthDtos.RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout account")
    public ResponseEntity<Void> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @Valid @RequestBody AuthDtos.LogoutRequest request,
            HttpServletRequest httpRequest
    ) {
        String accessToken = authorization.replace("Bearer ", "");
        authService.logout(accessToken, request, httpRequest.getRemoteAddr());
        return ResponseEntity.noContent().build();
    }
}
