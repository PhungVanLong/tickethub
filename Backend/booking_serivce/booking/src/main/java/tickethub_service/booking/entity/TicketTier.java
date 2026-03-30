package tickethub_service.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket_tiers_ref")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketTier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "event_id", nullable = false)
    private Long eventId;
    
    @Column(name = "tier_name", nullable = false, length = 100)
    private String tierName;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;
    
    @Builder.Default
    @Column(name = "sold_quantity", nullable = false)
    private Integer soldQuantity = 0;
    
    @Column(name = "max_per_user")
    private Integer maxPerUser;
    
    @Column(name = "sale_start_date")
    private LocalDateTime saleStartDate;
    
    @Column(name = "sale_end_date")
    private LocalDateTime saleEndDate;
    
    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "ticketTier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    
    @OneToMany(mappedBy = "ticketTier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TicketPromotion> ticketPromotions;
}
