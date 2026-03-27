package com.tickethub.indentity.controller;

import com.tickethub.indentity.dto.UserDtos;
import com.tickethub.indentity.entity.User;
import com.tickethub.indentity.service.LoggingService;
import com.tickethub.indentity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {
    private final UserService userService;
    private final LoggingService loggingService;

    public UserController(UserService userService, LoggingService loggingService) {
        this.userService = userService;
        this.loggingService = loggingService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user info")
    public UserDtos.UserResponse me(Authentication authentication) {
        return userService.getMe(authentication.getName());
    }

    @PutMapping("/me")
    @Operation(summary = "Update current user profile")
    public UserDtos.UserResponse updateMe(
            Authentication authentication,
            @Valid @RequestBody UserDtos.UpdateMeRequest request,
            HttpServletRequest httpRequest
    ) {
        User user = userService.getByEmail(authentication.getName());
        loggingService.logSystem(user, "UPDATE_PROFILE", "USER", user.getId(), "{\"status\":\"updated\"}", httpRequest.getRemoteAddr());
        return userService.updateMe(authentication.getName(), request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user details (admin only)")
    public UserDtos.UserResponse getUserById(@PathVariable UUID id) {
        return userService.getById(id);
    }
}
