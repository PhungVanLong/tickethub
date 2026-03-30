package management_service.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import management_service.management.entity.enums.RoleEventStaff;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_staff")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventStaff {
    
    @EmbeddedId
    private EventStaffId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("staffId")
    @JoinColumn(name = "staff_id", nullable = false)
    private User staff;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role_in_event", nullable = false)
    private RoleEventStaff roleInEvent;
    
    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;
    
    @PrePersist
    protected void onCreate() {
        assignedAt = LocalDateTime.now();
    }
}
