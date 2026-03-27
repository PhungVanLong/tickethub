package com.tickethub.indentity.repository;

import com.tickethub.indentity.entity.VouchersUsages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoucherUsageRepository extends JpaRepository<VouchersUsages, UUID> {
}
