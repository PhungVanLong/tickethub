package com.tickethub.indentity.repository;

import com.tickethub.indentity.entity.Vouchers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VouchersRepository extends JpaRepository<Vouchers, UUID> {
    Optional<Vouchers> findByCode(String code);
}
