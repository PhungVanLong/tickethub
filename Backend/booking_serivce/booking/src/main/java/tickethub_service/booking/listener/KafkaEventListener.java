package tickethub_service.booking.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;
import tickethub_service.booking.entity.User;
import tickethub_service.booking.repository.UserRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventListener {
    
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    
    @KafkaListener(topics = "user-events", groupId = "booking-service-group")
    public void handleUserEvent(String message) {
        try {
            log.info("Received user event: {}", message);
            
            // Parse user event
            UserEvent userEvent = objectMapper.readValue(message, UserEvent.class);
            
            switch (userEvent.getType()) {
                case "USER_CREATED":
                case "USER_UPDATED":
                    syncUser(userEvent.getUser());
                    break;
                case "USER_DELETED":
                    deleteUser(userEvent.getUser().getId());
                    break;
                default:
                    log.warn("Unknown user event type: {}", userEvent.getType());
            }
            
        } catch (Exception e) {
            log.error("Error processing user event: {}", message, e);
        }
    }
    
    @KafkaListeners({
        @KafkaListener(topics = "booking-events", groupId = "booking-service-group"),
        @KafkaListener(topics = "ticket-events", groupId = "booking-service-group"),
        @KafkaListener(topics = "payment-events", groupId = "booking-service-group")
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
    
    private void syncUser(User userData) {
        try {
            userRepository.findById(userData.getId())
                .ifPresentOrElse(existingUser -> {
                    // Update existing user
                    existingUser.setFullName(userData.getFullName());
                    existingUser.setEmail(userData.getEmail());
                    existingUser.setRole(userData.getRole());
                    userRepository.save(existingUser);
                    log.info("Updated user: {}", userData.getId());
                }, () -> {
                    // Create new user
                    userRepository.save(userData);
                    log.info("Created new user: {}", userData.getId());
                });
        } catch (Exception e) {
            log.error("Error syncing user: {}", userData.getId(), e);
        }
    }
    
    private void deleteUser(UUID userId) {
        try {
            userRepository.findById(userId).ifPresent(user -> {
                userRepository.delete(user);
                log.info("Deleted user: {}", userId);
            });
        } catch (Exception e) {
            log.error("Error deleting user: {}", userId, e);
        }
    }
    
    // DTO for user events
    public static class UserEvent {
        private String type;
        private User user;
        
        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public User getUser() { return user; }
        public void setUser(User user) { this.user = user; }
    }
}
