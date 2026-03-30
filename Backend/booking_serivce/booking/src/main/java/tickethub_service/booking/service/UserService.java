package tickethub_service.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickethub_service.booking.entity.User;
import tickethub_service.booking.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    public User createUser(User user) {
        log.info("Creating user with username: {}", user.getUsername());
        
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        return savedUser;
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User updateUser(Long id, User userDetails) {
        log.info("Updating user with ID: {}", id);
        
        User user = getUserById(id);
        
        user.setFullName(userDetails.getFullName());
        user.setPhone(userDetails.getPhone());
        user.setAddress(userDetails.getAddress());
        user.setAvatar(userDetails.getAvatar());
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        return updatedUser;
    }
    
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        
        User user = getUserById(id);
        userRepository.delete(user);
        
        log.info("User deleted successfully with ID: {}", id);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User verifyUser(Long id) {
        log.info("Verifying user with ID: {}", id);
        
        User user = getUserById(id);
        user.setIsVerified(true);
        user.setUpdatedAt(LocalDateTime.now());
        
        User verifiedUser = userRepository.save(user);
        log.info("User verified successfully with ID: {}", verifiedUser.getId());
        return verifiedUser;
    }
    
    public User deactivateUser(Long id) {
        log.info("Deactivating user with ID: {}", id);
        
        User user = getUserById(id);
        user.setIsActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        
        User deactivatedUser = userRepository.save(user);
        log.info("User deactivated successfully with ID: {}", deactivatedUser.getId());
        return deactivatedUser;
    }
    
    public User activateUser(Long id) {
        log.info("Activating user with ID: {}", id);
        
        User user = getUserById(id);
        user.setIsActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        
        User activatedUser = userRepository.save(user);
        log.info("User activated successfully with ID: {}", activatedUser.getId());
        return activatedUser;
    }
}
