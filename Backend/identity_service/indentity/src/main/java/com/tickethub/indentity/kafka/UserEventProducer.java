package com.tickethub.indentity.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickethub.indentity.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventProducer {
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    public void publishUserCreated(User user) {
        try {
            UserEvent event = new UserEvent();
            event.setType("USER_CREATED");
            event.setUser(convertToBookingUser(user));
            
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user-events", eventJson);
            
            log.info("Published USER_CREATED event for user: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Error publishing user created event", e);
        }
    }
    
    public void publishUserUpdated(User user) {
        try {
            UserEvent event = new UserEvent();
            event.setType("USER_UPDATED");
            event.setUser(convertToBookingUser(user));
            
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user-events", eventJson);
            
            log.info("Published USER_UPDATED event for user: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Error publishing user updated event", e);
        }
    }
    
    private BookingUser convertToBookingUser(User identityUser) {
        BookingUser bookingUser = new BookingUser();
        bookingUser.setId(identityUser.getId().toString());
        bookingUser.setFullName(identityUser.getFullName());
        bookingUser.setEmail(identityUser.getEmail());
        bookingUser.setRole(identityUser.getRole());
        return bookingUser;
    }
    
    // DTO for user events
    public static class UserEvent {
        private String type;
        private BookingUser user;
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public BookingUser getUser() { return user; }
        public void setUser(BookingUser user) { this.user = user; }
    }
    
    // Simple user DTO for booking service
    public static class BookingUser {
        private String id;
        private String fullName;
        private String email;
        private String role;
        
        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
