package management_service.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import management_service.management.entity.enums.ApprovalDecision;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_approvals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventApproval {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalDecision decision;
    
    private String reason;
    
    @Column(name = "decided_at")
    private LocalDateTime decidedAt;
}
