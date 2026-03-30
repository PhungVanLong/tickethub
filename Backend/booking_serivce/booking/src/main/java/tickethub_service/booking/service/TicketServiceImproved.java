package tickethub_service.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickethub_service.booking.entity.Ticket;
import tickethub_service.booking.entity.OrderItem;
import tickethub_service.booking.entity.enums.TicketStatus;
import tickethub_service.booking.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TicketServiceImproved {
    
    private final TicketRepository ticketRepository;
    
    public Ticket validateTicket(String ticketCode) {
        log.info("Validating ticket with code: {}", ticketCode);
        
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        
        if (ticket.getTicketStatus() != TicketStatus.VALID) {
            throw new RuntimeException("Ticket is not valid. Current status: " + ticket.getTicketStatus());
        }
        
        return ticket;
    }
    
    public Ticket useTicket(String ticketCode) {
        log.info("Using ticket with code: {}", ticketCode);
        
        Ticket ticket = validateTicket(ticketCode);
        
        ticket.setTicketStatus(TicketStatus.USED);
        ticket.setUsedAt(LocalDateTime.now());
        
        ticket = ticketRepository.save(ticket);
        log.info("Ticket used successfully with ID: {}", ticket.getId());
        return ticket;
    }
    
    public List<Ticket> getTicketsByUserId(Long userId) {
        log.info("Getting tickets for user ID: {}", userId);
        return ticketRepository.findByUserId(userId);
    }
    
    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        log.info("Getting tickets with status: {}", status);
        return ticketRepository.findByTicketStatus(status);
    }
    
    public Ticket generateTicket(OrderItem orderItem, String seatNumber) {
        log.info("Generating ticket for order item ID: {}", orderItem.getId());
        
        String ticketCode = generateTicketCode();
        String qrCode = generateQRCode(ticketCode);
        
        Ticket ticket = Ticket.builder()
                .orderItem(orderItem)
                .ticketCode(ticketCode)
                .qrCode(qrCode)
                .ticketStatus(TicketStatus.VALID)
                .seatNumber(seatNumber)
                .build();
        
        ticket = ticketRepository.save(ticket);
        log.info("Ticket generated successfully with code: {}", ticketCode);
        return ticket;
    }
    
    public List<Ticket> generateTicketsForOrderItem(OrderItem orderItem) {
        // Generate tickets for each quantity in the order item
        return java.util.stream.IntStream.range(0, orderItem.getQuantity())
                .mapToObj(i -> generateTicket(orderItem, "Seat-" + (i + 1)))
                .collect(java.util.stream.Collectors.toList());
    }
    
    public void cancelTicket(String ticketCode) {
        log.info("Cancelling ticket with code: {}", ticketCode);
        
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        
        if (ticket.getTicketStatus() == TicketStatus.USED) {
            throw new RuntimeException("Cannot cancel used ticket");
        }
        
        ticket.setTicketStatus(TicketStatus.CANCELLED);
        ticketRepository.save(ticket);
        
        log.info("Ticket cancelled successfully with code: {}", ticketCode);
    }
    
    public Ticket expireTicket(String ticketCode) {
        log.info("Expiring ticket with code: {}", ticketCode);
        
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        
        if (ticket.getTicketStatus() == TicketStatus.USED) {
            throw new RuntimeException("Cannot expire used ticket");
        }
        
        ticket.setTicketStatus(TicketStatus.EXPIRED);
        ticketRepository.save(ticket);
        
        log.info("Ticket expired successfully with code: {}", ticketCode);
        return ticket;
    }
    
    public List<Ticket> getExpiredTickets() {
        log.info("Getting expired tickets");
        return ticketRepository.findByTicketStatus(TicketStatus.EXPIRED);
    }
    
    public List<Ticket> getUsedTickets() {
        log.info("Getting used tickets");
        return ticketRepository.findByTicketStatus(TicketStatus.USED);
    }
    
    public List<Ticket> getValidTickets() {
        log.info("Getting valid tickets");
        return ticketRepository.findByTicketStatus(TicketStatus.VALID);
    }
    
    public String generateTicketCode() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private String generateQRCode(String ticketCode) {
        // In a real implementation, this would generate a proper QR code
        // For now, return a base64 encoded string or URL
        return "QR-" + ticketCode;
    }
}
