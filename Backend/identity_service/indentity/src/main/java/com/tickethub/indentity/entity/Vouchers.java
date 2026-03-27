package com.tickethub.indentity.entity;

import com.tickethub.indentity.entity.enums.DiscountType;
import com.tickethub.indentity.entity.enums.PromoApplyOn;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "vouchers")
public class Vouchers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private double discountValue;
    private double minOrderValue;
    private int usageLimit;
    private int usedCount;

    private LocalDateTime validFrom;
    private LocalDateTime validUntil;

    @Enumerated(EnumType.STRING)
    private PromoApplyOn applyOn;

    private boolean isActive;
}
