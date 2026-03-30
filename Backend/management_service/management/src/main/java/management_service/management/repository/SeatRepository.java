package management_service.management.repository;

import management_service.management.entity.Seat;
import management_service.management.entity.SeatMap;
import management_service.management.entity.TicketTier;
import management_service.management.entity.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    
    List<Seat> findBySeatMap(SeatMap seatMap);
    
    List<Seat> findByTicketTier(TicketTier ticketTier);
    
    List<Seat> findByStatus(SeatStatus status);
    
    Optional<Seat> findBySeatCode(String seatCode);
    
    @Query("SELECT s FROM Seat s WHERE s.seatMap.id = :seatMapId AND s.status = :status")
    List<Seat> findBySeatMapIdAndStatus(@Param("seatMapId") Long seatMapId, @Param("status") SeatStatus status);
    
    @Query("SELECT COUNT(s) FROM Seat s WHERE s.seatMap.id = :seatMapId AND s.status = 'AVAILABLE'")
    long countAvailableSeatsBySeatMapId(@Param("seatMapId") Long seatMapId);
    
    @Query("SELECT s FROM Seat s WHERE s.rowLabel = :rowLabel AND s.colNumber = :colNumber AND s.seatMap.id = :seatMapId")
    Optional<Seat> findByPosition(@Param("rowLabel") String rowLabel, @Param("colNumber") Integer colNumber, @Param("seatMapId") Long seatMapId);
}
