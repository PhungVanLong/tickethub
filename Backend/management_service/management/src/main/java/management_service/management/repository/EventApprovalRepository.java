package management_service.management.repository;

import management_service.management.entity.Event;
import management_service.management.entity.EventApproval;
import management_service.management.entity.User;
import management_service.management.entity.enums.ApprovalDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventApprovalRepository extends JpaRepository<EventApproval, Long> {
    
    List<EventApproval> findByEvent(Event event);
    
    List<EventApproval> findByAdmin(User admin);
    
    Optional<EventApproval> findByEventAndAdmin(Event event, User admin);
    
    List<EventApproval> findByDecision(ApprovalDecision decision);
    
    @Query("SELECT ea FROM EventApproval ea WHERE ea.event.id = :eventId AND ea.decision = :decision")
    List<EventApproval> findByEventIdAndDecision(@Param("eventId") Long eventId, @Param("decision") ApprovalDecision decision);
    
    @Query("SELECT COUNT(ea) FROM EventApproval ea WHERE ea.decision = 'APPROVED' AND ea.event.id = :eventId")
    long countApprovalsByEventId(@Param("eventId") Long eventId);
}
