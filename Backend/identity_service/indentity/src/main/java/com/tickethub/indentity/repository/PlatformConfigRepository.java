package com.tickethub.indentity.repository;

import com.tickethub.indentity.entity.PlatformConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlatformConfigRepository extends JpaRepository<PlatformConfig, UUID> {
    Optional<PlatformConfig> findByConfigKey(String configKey);
}
