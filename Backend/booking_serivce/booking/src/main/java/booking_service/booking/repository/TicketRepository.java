package booking_service.booking.repository;

import booking_service.booking.entity.Ticket;
import booking_service.booking.entity.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    
    List<Ticket> findByUserId(UUID userId);
    
    List<Ticket> findByOrderId(UUID orderId);
    
    List<Ticket> findByEventId(UUID eventId);
    
    List<Ticket> findByTicketStatus(TicketStatus ticketStatus);
    
    List<Ticket> findByUserIdAndTicketStatus(UUID userId, TicketStatus ticketStatus);
    
    @Query("SELECT t FROM Ticket t WHERE t.createdAt BETWEEN :startDate AND :endDate")
    List<Ticket> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    boolean existsByTicketNumber(String ticketNumber);
}
