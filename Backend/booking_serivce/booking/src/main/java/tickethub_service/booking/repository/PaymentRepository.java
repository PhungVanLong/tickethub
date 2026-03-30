package tickethub_service.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tickethub_service.booking.entity.Payment;
import tickethub_service.booking.entity.enums.PaymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    
    Optional<Payment> findByPaymentCode(String paymentCode);
    
    List<Payment> findByOrderId(UUID orderId);
    
    List<Payment> findByPaymentStatus(PaymentStatus status);
}
