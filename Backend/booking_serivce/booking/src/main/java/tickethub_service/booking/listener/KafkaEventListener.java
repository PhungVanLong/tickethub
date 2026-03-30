package tickethub_service.booking.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

//@Component
//@RequiredArgsConstructor
//@Slf4j
public class KafkaEventListener {
    
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(KafkaEventListener.class);
    
    @KafkaListeners({
        @KafkaListener(topics = "booking-events", groupId = "booking-service-group"),
        @KafkaListener(topics = "ticket-events", groupId = "booking-service-group"),
        @KafkaListener(topics = "payment-events", groupId = "booking-service-group"),
        @KafkaListener(topics = "user-events", groupId = "booking-service-group")
    })
    public void handleGenericEvent(String message) {
        try {
            log.info("Received event: {}", message);
            
            // Simple event processing - just log for now
            // In production, you would parse the message and route to appropriate handlers
            
        } catch (Exception e) {
            log.error("Error processing event: {}", message, e);
            // In production, you might want to send to a dead-letter queue
        }
    }
}
