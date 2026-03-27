package com.tickethub.indentity.controller;

import com.tickethub.indentity.entity.CaptchaLogs;
import com.tickethub.indentity.entity.SystemLogs;
import com.tickethub.indentity.service.LoggingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Logs")
public class LogsController {

    private final LoggingService loggingService;

    public LogsController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping("/system/logs")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "View system logs")
    public List<SystemLogs> systemLogs() {
        return loggingService.getSystemLogs();
    }

    @GetMapping("/captcha/logs")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "View captcha logs")
    public List<CaptchaLogs> captchaLogs() {
        return loggingService.getCaptchaLogs();
    }
}
