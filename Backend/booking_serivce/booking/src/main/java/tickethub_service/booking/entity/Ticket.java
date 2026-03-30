package tickethub_service.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tickethub_service.booking.entity.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;
    
    @Column(name = "ticket_code", nullable = false, unique = true, length = 50)
    private String ticketCode;
    
    @Column(name = "qr_code", length = 255)
    private String qrCode;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false, length = 20)
    private TicketStatus ticketStatus;
    
    @Column(name = "seat_number", length = 20)
    private String seatNumber;
    
    @Column(name = "used_at")
    private LocalDateTime usedAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
