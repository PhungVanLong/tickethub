package management_service.management.service;

import lombok.RequiredArgsConstructor;
import management_service.management.entity.Event;
import management_service.management.entity.EventApproval;
import management_service.management.entity.User;
import management_service.management.entity.enums.ApprovalDecision;
import management_service.management.entity.enums.EventStatus;
import management_service.management.repository.EventApprovalRepository;
import management_service.management.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventApprovalService {
    
    private final EventApprovalRepository eventApprovalRepository;
    private final EventRepository eventRepository;
    private final UserService userService;
    
    public EventApproval createApproval(Long eventId, Long adminId, ApprovalDecision decision, String reason) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        
        User admin = userService.getUserById(adminId)
            .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));
        
        Optional<EventApproval> existingApproval = eventApprovalRepository.findByEventAndAdmin(event, admin);
        if (existingApproval.isPresent()) {
            throw new RuntimeException("Admin has already reviewed this event");
        }
        
        EventApproval approval = EventApproval.builder()
            .event(event)
            .admin(admin)
            .decision(decision)
            .reason(reason)
            .decidedAt(LocalDateTime.now())
            .build();
        
        EventApproval savedApproval = eventApprovalRepository.save(approval);
        
        updateEventStatusBasedOnApprovals(event);
        
        return savedApproval;
    }
    
    @Transactional(readOnly = true)
    public Optional<EventApproval> getApprovalById(Long id) {
        return eventApprovalRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<EventApproval> getApprovalsByEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        return eventApprovalRepository.findByEvent(event);
    }
    
    @Transactional(readOnly = true)
    public List<EventApproval> getApprovalsByAdmin(Long adminId) {
        User admin = userService.getUserById(adminId)
            .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));
        return eventApprovalRepository.findByAdmin(admin);
    }
    
    @Transactional(readOnly = true)
    public List<EventApproval> getApprovalsByDecision(ApprovalDecision decision) {
        return eventApprovalRepository.findByDecision(decision);
    }
    
    public EventApproval updateApproval(Long id, ApprovalDecision decision, String reason) {
        EventApproval approval = eventApprovalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Approval not found with id: " + id));
        
        approval.setDecision(decision);
        approval.setReason(reason);
        approval.setDecidedAt(LocalDateTime.now());
        
        EventApproval updatedApproval = eventApprovalRepository.save(approval);
        updateEventStatusBasedOnApprovals(approval.getEvent());
        
        return updatedApproval;
    }
    
    public void deleteApproval(Long id) {
        EventApproval approval = eventApprovalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Approval not found with id: " + id));
        
        Event event = approval.getEvent();
        eventApprovalRepository.delete(approval);
        updateEventStatusBasedOnApprovals(event);
    }
    
    @Transactional(readOnly = true)
    public long getApprovalCountByEvent(Long eventId) {
        return eventApprovalRepository.countApprovalsByEventId(eventId);
    }
    
    private void updateEventStatusBasedOnApprovals(Event event) {
        long approvalCount = eventApprovalRepository.countApprovalsByEventId(event.getId());
        
        if (approvalCount == 0) {
            event.setStatus(EventStatus.PENDING_APPROVAL);
        } else {
            List<EventApproval> approvals = eventApprovalRepository.findByEvent(event);
            boolean hasRejection = approvals.stream()
                .anyMatch(approval -> approval.getDecision() == ApprovalDecision.REJECTED);
            
            if (hasRejection) {
                event.setStatus(EventStatus.REJECTED);
            } else {
                event.setStatus(EventStatus.APPROVED);
            }
        }
        
        eventRepository.save(event);
    }
}
