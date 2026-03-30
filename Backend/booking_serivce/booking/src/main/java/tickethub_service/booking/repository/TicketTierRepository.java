package tickethub_service.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tickethub_service.booking.entity.TicketTier;

import java.util.List;

@Repository
public interface TicketTierRepository extends JpaRepository<TicketTier, Long> {
    
    List<TicketTier> findByEventId(Long eventId);
    
    List<TicketTier> findByEventIdAndIsActive(Long eventId, Boolean isActive);
    
    @Query("SELECT tt FROM TicketTier tt WHERE tt.eventId = :eventId AND tt.isActive = true AND tt.availableQuantity > tt.soldQuantity")
    List<TicketTier> findAvailableTicketTiers(@Param("eventId") Long eventId);
    
    List<TicketTier> findByIsActive(Boolean isActive);
}
