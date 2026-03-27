package com.tickethub.indentity.repository;

import com.tickethub.indentity.entity.SystemLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SystemLogsRepository extends JpaRepository<SystemLogs, UUID> {
}
