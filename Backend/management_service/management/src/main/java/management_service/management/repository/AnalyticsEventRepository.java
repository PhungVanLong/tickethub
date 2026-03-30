package management_service.management.repository;

import management_service.management.entity.AnalyticsEvent;
import management_service.management.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEvent, Long> {
    
    List<AnalyticsEvent> findByEvent(Event event);
    
    @Query("SELECT ae FROM AnalyticsEvent ae WHERE ae.event.id = :eventId ORDER BY ae.snapshotAt DESC")
    List<AnalyticsEvent> findByEventIdOrderBySnapshotAtDesc(@Param("eventId") Long eventId);
    
    @Query("SELECT ae FROM AnalyticsEvent ae WHERE ae.snapshotAt BETWEEN :start AND :end")
    List<AnalyticsEvent> findAnalyticsBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT ae FROM AnalyticsEvent ae WHERE ae.event.id = :eventId AND ae.snapshotAt = " +
           "(SELECT MAX(ae2.snapshotAt) FROM AnalyticsEvent ae2 WHERE ae2.event.id = :eventId)")
    AnalyticsEvent findLatestByEventId(@Param("eventId") Long eventId);
    
    @Query("SELECT COUNT(ae) FROM AnalyticsEvent ae WHERE ae.event.id = :eventId")
    long countByEventId(@Param("eventId") Long eventId);
}
