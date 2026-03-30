package management_service.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import management_service.management.entity.enums.TierType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket_tiers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketTier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_map_id", nullable = false)
    private SeatMap seatMap;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tier_type", nullable = false)
    private TierType tierType;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "quantity_total", nullable = false)
    private Integer quantityTotal;
    
    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;
    
    @Column(name = "color_code", nullable = false)
    private String colorCode;
    
    @Column(name = "sale_start")
    private LocalDateTime saleStart;
    
    @Column(name = "sale_end")
    private LocalDateTime saleEnd;
    
    @OneToMany(mappedBy = "ticketTier")
    private List<Seat> seats;
}
