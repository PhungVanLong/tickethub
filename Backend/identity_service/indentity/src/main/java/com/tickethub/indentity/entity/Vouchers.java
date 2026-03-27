package com.tickethub.indentity.entity;

import com.tickethub.indentity.entity.enums.DiscountType;
import com.tickethub.indentity.entity.enums.PromoApplyOn;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
public class Vouchers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "discount_type", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false, precision = 18, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "min_order_value", precision = 18, scale = 2)
    private BigDecimal minOrderValue;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "used_count", nullable = false)
    private Integer usedCount;

    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_until", nullable = false)
    private LocalDateTime validUntil;

    @Column(name = "apply_on", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private PromoApplyOn applyOn;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @PrePersist
    public void prePersist() {
        if (usedCount == null) {
            usedCount = 0;
        }
    }
}
