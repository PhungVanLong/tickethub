package com.tickethub.indentity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vouchers_usages")
public class VouchersUsages {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Vouchers voucher;

    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double discountApplied;
    private LocalDateTime usedAt;
}
