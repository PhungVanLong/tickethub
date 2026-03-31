package management_service.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import management_service.management.entity.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users_ref")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    
    @Column(name = "synced_at")
    private LocalDateTime syncedAt;
    
    @OneToMany(mappedBy = "organizer")
    @JsonIgnore
    private List<Event> events;
    
    @OneToMany(mappedBy = "admin")
    private List<EventApproval> eventApprovals;
    
    @OneToMany(mappedBy = "staff")
    private List<EventStaff> eventStaff;
}
