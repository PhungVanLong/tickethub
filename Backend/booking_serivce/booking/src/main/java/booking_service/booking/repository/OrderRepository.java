package booking_service.booking.repository;

import booking_service.booking.entity.Order;
import booking_service.booking.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    
    Optional<Order> findByOrderNumber(String orderNumber);
    
    List<Order> findByUserId(UUID userId);
    
    List<Order> findByEventId(UUID eventId);
    
    List<Order> findByOrderStatus(OrderStatus orderStatus);
    
    List<Order> findByUserIdAndOrderStatus(UUID userId, OrderStatus orderStatus);
    
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT o FROM Order o WHERE o.expiredAt < :now AND o.orderStatus = :status")
    List<Order> findExpiredOrders(@Param("now") LocalDateTime now, @Param("status") OrderStatus status);
    
    boolean existsByOrderNumber(String orderNumber);
}
