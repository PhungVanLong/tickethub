package com.tickethub.indentity.repository;

import com.tickethub.indentity.entity.CaptchaLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CaptchaLogsRepository extends JpaRepository<CaptchaLogs, UUID> {
}
