package management_service.management.repository;

import management_service.management.entity.SeatMap;
import management_service.management.entity.TicketTier;
import management_service.management.entity.enums.TierType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketTierRepository extends JpaRepository<TicketTier, Long> {
    
    List<TicketTier> findBySeatMap(SeatMap seatMap);
    
    List<TicketTier> findByTierType(TierType tierType);
    
    @Query("SELECT tt FROM TicketTier tt WHERE tt.seatMap.id = :seatMapId")
    List<TicketTier> findBySeatMapId(@Param("seatMapId") Long seatMapId);
    
    @Query("SELECT tt FROM TicketTier tt WHERE tt.seatMap.id = :seatMapId AND tt.tierType = :tierType")
    List<TicketTier> findBySeatMapIdAndTierType(@Param("seatMapId") Long seatMapId, @Param("tierType") TierType tierType);
    
    @Query("SELECT COUNT(tt) FROM TicketTier tt WHERE tt.seatMap.id = :seatMapId")
    long countBySeatMapId(@Param("seatMapId") Long seatMapId);
    
    void deleteBySeatMap(SeatMap seatMap);
}
