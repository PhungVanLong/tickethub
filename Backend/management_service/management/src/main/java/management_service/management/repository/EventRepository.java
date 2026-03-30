package management_service.management.repository;

import management_service.management.entity.Event;
import management_service.management.entity.User;
import management_service.management.entity.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByOrganizer(User organizer);
    
    List<Event> findByStatus(EventStatus status);
    
    List<Event> findByCity(String city);
    
    @Query("SELECT e FROM Event e WHERE e.startTime >= :start AND e.endTime <= :end")
    List<Event> findEventsBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT e FROM Event e WHERE e.title LIKE %:title%")
    List<Event> searchByTitle(@Param("title") String title);
    
    @Query("SELECT COUNT(e) FROM Event e WHERE e.status = :status")
    long countByStatus(@Param("status") EventStatus status);
    
    @Query("SELECT e FROM Event e WHERE e.isPublished = true AND e.status = 'APPROVED'")
    List<Event> findPublishedEvents();
}
