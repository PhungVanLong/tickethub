package tickethub_service.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickethub_service.booking.entity.Ticket;
import tickethub_service.booking.entity.enums.TicketStatus;
import tickethub_service.booking.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TicketService {
    
    private final TicketRepository ticketRepository;
    
    public Ticket validateTicket(String ticketCode) {
        log.info("Validating ticket with code: {}", ticketCode);
        
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        
        if (ticket.getTicketStatus() != TicketStatus.VALID) {
            throw new RuntimeException("Ticket is not valid");
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
    
    public List<Ticket> getTicketsByUserId(UUID userId) {
        log.info("Getting tickets for user ID: {}", userId);
        return ticketRepository.findByUserId(userId);
    }
    
    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        log.info("Getting tickets with status: {}", status);
        return ticketRepository.findByTicketStatus(status);
    }
    
    public String generateTicketCode() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
