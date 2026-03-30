package tickethub_service.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickethub_service.booking.entity.Order;
import tickethub_service.booking.entity.Payment;
import tickethub_service.booking.entity.enums.PaymentStatus;
import tickethub_service.booking.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    
    public Payment createPayment(UUID orderId, String paymentMethod, BigDecimal amount) {
        log.info("Creating payment for order ID: {}, amount: {}", orderId, amount);
        
        Payment payment = Payment.builder()
                .order(Order.builder().id(orderId).build())
                .paymentCode(generatePaymentCode())
                .paymentMethod(paymentMethod)
                .amount(amount)
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        
        payment = paymentRepository.save(payment);
        log.info("Payment created with ID: {}", payment.getId());
        return payment;
    }
    
    public Payment updatePaymentStatus(UUID paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        payment.setPaymentStatus(status);
        if (status == PaymentStatus.COMPLETED) {
            payment.setPaidAt(LocalDateTime.now());
        }
        
        payment = paymentRepository.save(payment);
        log.info("Payment status updated to {} for payment ID: {}", status, paymentId);
        return payment;
    }
    
    public Payment getPaymentById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
    
    private String generatePaymentCode() {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
