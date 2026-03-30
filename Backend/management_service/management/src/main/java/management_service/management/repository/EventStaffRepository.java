package management_service.management.repository;

import management_service.management.entity.Event;
import management_service.management.entity.EventStaff;
import management_service.management.entity.EventStaffId;
import management_service.management.entity.User;
import management_service.management.entity.enums.RoleEventStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStaffRepository extends JpaRepository<EventStaff, EventStaffId> {
    
    List<EventStaff> findByEvent(Event event);
    
    List<EventStaff> findByStaff(User staff);
    
    List<EventStaff> findByRoleInEvent(RoleEventStaff role);
    
    @Query("SELECT es FROM EventStaff es WHERE es.event.id = :eventId AND es.roleInEvent = :role")
    List<EventStaff> findByEventIdAndRole(@Param("eventId") Long eventId, @Param("role") RoleEventStaff role);
    
    @Query("SELECT COUNT(es) FROM EventStaff es WHERE es.event.id = :eventId")
    long countStaffByEventId(@Param("eventId") Long eventId);
    
    void deleteByEvent(Event event);
}
