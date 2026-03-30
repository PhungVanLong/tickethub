package tickethub_service.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tickethub_service.booking.entity.Ticket;
import tickethub_service.booking.entity.enums.TicketStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    Optional<Ticket> findByTicketCode(String ticketCode);
    
    List<Ticket> findByTicketStatus(TicketStatus status);
    
    @Query("SELECT t FROM Ticket t WHERE t.orderItem.order.id = :orderId")
    List<Ticket> findByOrderId(@Param("orderId") Long orderId);
    
    @Query("SELECT t FROM Ticket t WHERE t.orderItem.order.user.id = :userId")
    List<Ticket> findByUserId(@Param("userId") Long userId);
    
    List<Ticket> findByOrderItemOrderUserId(Long userId);
}
