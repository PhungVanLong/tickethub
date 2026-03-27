package com.tickethub.indentity.service;

import com.tickethub.indentity.entity.CaptchaLogs;
import com.tickethub.indentity.entity.SystemLogs;
import com.tickethub.indentity.entity.User;
import com.tickethub.indentity.repository.CaptchaLogsRepository;
import com.tickethub.indentity.repository.SystemLogsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LoggingService {

    private final SystemLogsRepository systemLogsRepository;
    private final CaptchaLogsRepository captchaLogsRepository;

    public LoggingService(SystemLogsRepository systemLogsRepository, CaptchaLogsRepository captchaLogsRepository) {
        this.systemLogsRepository = systemLogsRepository;
        this.captchaLogsRepository = captchaLogsRepository;
    }

    public void logSystem(User user, String action, String entityType, UUID entityId, String metadata, String ipAddress) {
        SystemLogs logs = new SystemLogs();
        logs.setUserId(user == null ? null : user.getId());
        logs.setAction(action);
        logs.setEntityType(entityType);
        logs.setEntityId(entityId);
        logs.setMetadata(metadata);
        logs.setIpAddress(ipAddress);
        systemLogsRepository.save(logs);
    }

    public void logCaptcha(User user, String action, String token, boolean passed, Float score) {
        CaptchaLogs logs = new CaptchaLogs();
        logs.setUserId(user == null ? null : user.getId());
        logs.setAction(action);
        logs.setToken(token);
        logs.setPassed(passed);
        logs.setScore(score);
        captchaLogsRepository.save(logs);
    }

    public List<SystemLogs> getSystemLogs() {
        return systemLogsRepository.findAll();
    }

    public List<CaptchaLogs> getCaptchaLogs() {
        return captchaLogsRepository.findAll();
    }
}
