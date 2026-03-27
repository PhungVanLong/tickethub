package com.tickethub.indentity.service;

import com.tickethub.indentity.dto.PlatformConfigDtos;
import com.tickethub.indentity.entity.PlatformConfig;
import com.tickethub.indentity.entity.User;
import com.tickethub.indentity.repository.PlatformConfigRepository;
import com.tickethub.indentity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformConfigService {
    private final PlatformConfigRepository platformConfigRepository;
    private final UserRepository userRepository;
    private final LoggingService loggingService;

    public PlatformConfigService(
            PlatformConfigRepository platformConfigRepository,
            UserRepository userRepository,
            LoggingService loggingService
    ) {
        this.platformConfigRepository = platformConfigRepository;
        this.userRepository = userRepository;
        this.loggingService = loggingService;
    }

    public List<PlatformConfigDtos.ConfigResponse> getAllConfigs() {
        return platformConfigRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PlatformConfigDtos.ConfigResponse updateConfig(
            String key,
            PlatformConfigDtos.UpdateConfigRequest request,
            String currentEmail,
            String ipAddress
    ) {
        User actor = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PlatformConfig config = platformConfigRepository.findByConfigKey(key).orElseGet(() -> {
            PlatformConfig created = new PlatformConfig();
            created.setConfigKey(key);
            return created;
        });
        config.setConfigValue(request.configValue());
        config.setDescription(request.description());
        config.setUpdatedBy(actor.getId());

        PlatformConfig saved = platformConfigRepository.save(config);
        loggingService.logSystem(actor, "UPDATE_PLATFORM_CONFIG", "PLATFORM_CONFIG", saved.getId(), "{\"key\":\"" + key + "\"}", ipAddress);
        return toResponse(saved);
    }

    private PlatformConfigDtos.ConfigResponse toResponse(PlatformConfig config) {
        return new PlatformConfigDtos.ConfigResponse(
                config.getId(),
                config.getConfigKey(),
                config.getConfigValue(),
                config.getDescription(),
                config.getUpdatedBy(),
                config.getUpdatedAt()
        );
    }
}
