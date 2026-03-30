package tickethub_service.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tickethub_service.booking.entity.Ticket;
import tickethub_service.booking.entity.enums.TicketStatus;
import tickethub_service.booking.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TicketController {
    
    private final TicketService ticketService;
    
    @GetMapping("/validate/{ticketCode}")
    public ResponseEntity<Ticket> validateTicket(@PathVariable String ticketCode) {
        log.info("Validating ticket with code: {}", ticketCode);
        Ticket ticket = ticketService.validateTicket(ticketCode);
        return ResponseEntity.ok(ticket);
    }
    
    @PostMapping("/use/{ticketCode}")
    public ResponseEntity<Ticket> useTicket(@PathVariable String ticketCode) {
        log.info("Using ticket with code: {}", ticketCode);
        Ticket ticket = ticketService.useTicket(ticketCode);
        return ResponseEntity.ok(ticket);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Ticket>> getTicketsByUser(@PathVariable Long userId) {
        log.info("Getting tickets for user ID: {}", userId);
        List<Ticket> tickets = ticketService.getTicketsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Ticket>> getTicketsByStatus(@PathVariable TicketStatus status) {
        log.info("Getting tickets with status: {}", status);
        List<Ticket> tickets = ticketService.getTicketsByStatus(status);
        return ResponseEntity.ok(tickets);
    }
}
