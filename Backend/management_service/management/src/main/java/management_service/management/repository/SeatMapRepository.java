package management_service.management.repository;

import management_service.management.entity.Event;
import management_service.management.entity.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatMapRepository extends JpaRepository<SeatMap, Long> {
    
    List<SeatMap> findByEvent(Event event);
    
    @Query("SELECT sm FROM SeatMap sm WHERE sm.event.id = :eventId")
    List<SeatMap> findByEventId(@Param("eventId") Long eventId);
    
    @Query("SELECT COUNT(sm) FROM SeatMap sm WHERE sm.event.id = :eventId")
    long countByEventId(@Param("eventId") Long eventId);
    
    void deleteByEvent(Event event);
}
