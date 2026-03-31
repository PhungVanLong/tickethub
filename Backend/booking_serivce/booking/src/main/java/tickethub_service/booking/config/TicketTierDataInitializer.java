package tickethub_service.booking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tickethub_service.booking.entity.TicketTier;
import tickethub_service.booking.entity.User;
import tickethub_service.booking.repository.TicketTierRepository;
import tickethub_service.booking.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TicketTierDataInitializer implements CommandLineRunner {
    
    private final TicketTierRepository ticketTierRepository;
    private final UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize users first
        if (userRepository.count() == 0) {
            initializeSampleUsers();
        }
        
        // Then initialize ticket tiers
        if (ticketTierRepository.count() == 0) {
            initializeSampleTicketTiers();
        }
    }
    
    private void initializeSampleUsers() {
        // Create sample user for testing
        User testUser = User.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .fullName("John Doe")
                .email("john.doe@test.com")
                .role("CUSTOMER")
                .isActive(true)
                .isVerified(false)
                .syncedAt(LocalDateTime.now())
                .build();
        
        userRepository.save(testUser);
        System.out.println("Sample user initialized successfully!");
        System.out.println("- John Doe (john.doe@test.com) - ID: 550e8400-e29b-41d4-a716-446655440004");
    }
    
    private void initializeSampleTicketTiers() {
        // Sample event ID (you can replace with actual event ID from your system)
        UUID sampleEventId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        
        // Create sample ticket tiers
        TicketTier vipTier = TicketTier.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .eventId(sampleEventId)
                .tierName("VIP")
                .price(new BigDecimal("1500000.00"))
                .availableQuantity(100)
                .soldQuantity(0)
                .maxPerUser(5)
                .saleStartDate(LocalDateTime.now().minusDays(30))
                .saleEndDate(LocalDateTime.now().plusDays(30))
                .isActive(true)
                .build();
        
        TicketTier regularTier = TicketTier.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"))
                .eventId(sampleEventId)
                .tierName("Regular")
                .price(new BigDecimal("500000.00"))
                .availableQuantity(500)
                .soldQuantity(0)
                .maxPerUser(10)
                .saleStartDate(LocalDateTime.now().minusDays(30))
                .saleEndDate(LocalDateTime.now().plusDays(30))
                .isActive(true)
                .build();
        
        TicketTier economyTier = TicketTier.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"))
                .eventId(sampleEventId)
                .tierName("Economy")
                .price(new BigDecimal("200000.00"))
                .availableQuantity(1000)
                .soldQuantity(0)
                .maxPerUser(20)
                .saleStartDate(LocalDateTime.now().minusDays(30))
                .saleEndDate(LocalDateTime.now().plusDays(30))
                .isActive(true)
                .build();
        
        // Save all ticket tiers
        ticketTierRepository.save(vipTier);
        ticketTierRepository.save(regularTier);
        ticketTierRepository.save(economyTier);
        
        System.out.println("Sample ticket tiers initialized successfully!");
        System.out.println("- VIP Tier: 1,500,000 VND (100 available)");
        System.out.println("- Regular Tier: 500,000 VND (500 available)");
        System.out.println("- Economy Tier: 200,000 VND (1000 available)");
    }
}
