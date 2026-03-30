package management_service.management.controller;

import lombok.RequiredArgsConstructor;
import management_service.management.entity.EventApproval;
import management_service.management.entity.enums.ApprovalDecision;
import management_service.management.service.EventApprovalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event-approvals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventApprovalController {
    
    private final EventApprovalService eventApprovalService;
    
    @PostMapping
    public ResponseEntity<EventApproval> createApproval(
            @RequestParam Long eventId,
            @RequestParam Long adminId,
            @RequestParam ApprovalDecision decision,
            @RequestParam(required = false) String reason) {
        try {
            EventApproval approval = eventApprovalService.createApproval(eventId, adminId, decision, reason);
            return new ResponseEntity<>(approval, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EventApproval> getApprovalById(@PathVariable Long id) {
        return eventApprovalService.getApprovalById(id)
            .map(approval -> new ResponseEntity<>(approval, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventApproval>> getApprovalsByEvent(@PathVariable Long eventId) {
        try {
            List<EventApproval> approvals = eventApprovalService.getApprovalsByEvent(eventId);
            return new ResponseEntity<>(approvals, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<EventApproval>> getApprovalsByAdmin(@PathVariable Long adminId) {
        try {
            List<EventApproval> approvals = eventApprovalService.getApprovalsByAdmin(adminId);
            return new ResponseEntity<>(approvals, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/decision/{decision}")
    public ResponseEntity<List<EventApproval>> getApprovalsByDecision(@PathVariable ApprovalDecision decision) {
        List<EventApproval> approvals = eventApprovalService.getApprovalsByDecision(decision);
        return new ResponseEntity<>(approvals, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EventApproval> updateApproval(
            @PathVariable Long id,
            @RequestParam ApprovalDecision decision,
            @RequestParam(required = false) String reason) {
        try {
            EventApproval updatedApproval = eventApprovalService.updateApproval(id, decision, reason);
            return new ResponseEntity<>(updatedApproval, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable Long id) {
        try {
            eventApprovalService.deleteApproval(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/count/{eventId}")
    public ResponseEntity<Long> getApprovalCountByEvent(@PathVariable Long eventId) {
        long count = eventApprovalService.getApprovalCountByEvent(eventId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
