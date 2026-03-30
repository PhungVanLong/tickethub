package tickethub_service.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tickethub_service.booking.entity.Order;
import tickethub_service.booking.entity.User;
import tickethub_service.booking.entity.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    
    List<Order> findByUser(User user);
    
    List<Order> findByUserId(UUID userId);
    
    List<Order> findByOrderStatus(OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.orderStatus = :status")
    List<Order> findByUserIdAndStatus(@Param("userId") UUID userId, @Param("status") OrderStatus status);
    
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
