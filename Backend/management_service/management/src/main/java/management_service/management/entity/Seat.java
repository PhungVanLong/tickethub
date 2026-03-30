package management_service.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import management_service.management.entity.enums.SeatStatus;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_map_id", nullable = false)
    private SeatMap seatMap;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_tier_id")
    private TicketTier ticketTier;
    
    @Column(name = "row_label", nullable = false)
    private String rowLabel;
    
    @Column(name = "col_number", nullable = false)
    private Integer colNumber;
    
    @Column(name = "seat_code", nullable = false, unique = true)
    private String seatCode;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;
}
