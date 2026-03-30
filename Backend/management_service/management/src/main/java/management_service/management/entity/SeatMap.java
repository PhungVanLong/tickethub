package management_service.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "seat_maps")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatMap {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "layout_json", columnDefinition = "JSON")
    private String layoutJson;
    
    @Column(name = "total_rows", nullable = false)
    private Integer totalRows;
    
    @Column(name = "total_cols", nullable = false)
    private Integer totalCols;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @OneToMany(mappedBy = "seatMap")
    private List<TicketTier> ticketTiers;
    
    @OneToMany(mappedBy = "seatMap")
    private List<Seat> seats;
}
