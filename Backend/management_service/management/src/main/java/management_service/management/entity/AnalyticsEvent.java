package management_service.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    
    @Column(name = "total_tickets_sold", nullable = false)
    private Integer totalTicketsSold;
    
    @Column(name = "total_checkins", nullable = false)
    private Integer totalCheckins;
    
    @Column(name = "total_revenue", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalRevenue;
    
    @Column(name = "tier_breakdown", columnDefinition = "JSON")
    private String tierBreakdown;
    
    @Column(name = "snapshot_at", nullable = false)
    private LocalDateTime snapshotAt;
}
