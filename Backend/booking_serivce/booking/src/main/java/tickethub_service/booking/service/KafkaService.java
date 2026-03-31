package tickethub_service.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    public void sendOrderEvent(String eventType, Object eventData) {
        try {
            String message = objectMapper.writeValueAsString(eventData);
            String topic = "booking-events";
            
            log.info("Sending order event: {} to topic: {}", eventType, topic);
            kafkaTemplate.send(topic, message);
            
        } catch (Exception e) {
            log.error("Failed to send order event: {}", eventType, e);
        }
    }
    
    public void sendTicketEvent(String eventType, Object eventData) {
        try {
            String message = objectMapper.writeValueAsString(eventData);
            String topic = "ticket-events";
            
            log.info("Sending ticket event: {} to topic: {}", eventType, topic);
            kafkaTemplate.send(topic, message);
            
        } catch (Exception e) {
            log.error("Failed to send ticket event: {}", eventType, e);
        }
    }
    
    public void sendPaymentEvent(String eventType, Object eventData) {
        try {
            String message = objectMapper.writeValueAsString(eventData);
            String topic = "payment-events";
            
            log.info("Sending payment event: {} to topic: {}", eventType, topic);
            kafkaTemplate.send(topic, message);
            
        } catch (Exception e) {
            log.error("Failed to send payment event: {}", eventType, e);
        }
    }
    
    public void sendUserEvent(String eventType, Object eventData) {
        try {
            String message = objectMapper.writeValueAsString(eventData);
            String topic = "user-events";
            
            log.info("Sending user event: {} to topic: {}", eventType, topic);
            kafkaTemplate.send(topic, message);
            
        } catch (Exception e) {
            log.error("Failed to send user event: {}", eventType, e);
        }
    }
}
