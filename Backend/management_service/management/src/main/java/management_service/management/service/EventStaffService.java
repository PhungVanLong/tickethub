package management_service.management.service;

import lombok.RequiredArgsConstructor;
import management_service.management.entity.Event;
import management_service.management.entity.EventStaff;
import management_service.management.entity.EventStaffId;
import management_service.management.entity.User;
import management_service.management.entity.enums.RoleEventStaff;
import management_service.management.repository.EventStaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventStaffService {
    
    private final EventStaffRepository eventStaffRepository;
    private final EventService eventService;
    private final UserService userService;
    
    public EventStaff assignStaffToEvent(Long eventId, Long staffId, RoleEventStaff role) {
        Event event = eventService.getEventById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        
        User staff = userService.getUserById(staffId)
            .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
        
        EventStaffId id = new EventStaffId(eventId, staffId);
        
        if (eventStaffRepository.existsById(id)) {
            throw new RuntimeException("Staff already assigned to this event");
        }
        
        EventStaff eventStaff = EventStaff.builder()
            .id(id)
            .event(event)
            .staff(staff)
            .roleInEvent(role)
            .assignedAt(LocalDateTime.now())
            .build();
        
        return eventStaffRepository.save(eventStaff);
    }
    
    @Transactional(readOnly = true)
    public Optional<EventStaff> getEventStaffById(Long eventId, Long staffId) {
        EventStaffId id = new EventStaffId(eventId, staffId);
        return eventStaffRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<EventStaff> getStaffByEvent(Long eventId) {
        Event event = eventService.getEventById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        return eventStaffRepository.findByEvent(event);
    }
    
    @Transactional(readOnly = true)
    public List<EventStaff> getEventsByStaff(Long staffId) {
        User staff = userService.getUserById(staffId)
            .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
        return eventStaffRepository.findByStaff(staff);
    }
    
    @Transactional(readOnly = true)
    public List<EventStaff> getStaffByRole(RoleEventStaff role) {
        return eventStaffRepository.findByRoleInEvent(role);
    }
    
    @Transactional(readOnly = true)
    public List<EventStaff> getStaffByEventAndRole(Long eventId, RoleEventStaff role) {
        return eventStaffRepository.findByEventIdAndRole(eventId, role);
    }
    
    public EventStaff updateStaffRole(Long eventId, Long staffId, RoleEventStaff newRole) {
        EventStaff eventStaff = getEventStaffById(eventId, staffId)
            .orElseThrow(() -> new RuntimeException("Staff assignment not found"));
        
        eventStaff.setRoleInEvent(newRole);
        return eventStaffRepository.save(eventStaff);
    }
    
    public void removeStaffFromEvent(Long eventId, Long staffId) {
        EventStaffId id = new EventStaffId(eventId, staffId);
        if (!eventStaffRepository.existsById(id)) {
            throw new RuntimeException("Staff assignment not found");
        }
        eventStaffRepository.deleteById(id);
    }
    
    public void removeAllStaffFromEvent(Long eventId) {
        Event event = eventService.getEventById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        eventStaffRepository.deleteByEvent(event);
    }
    
    @Transactional(readOnly = true)
    public long getStaffCountByEvent(Long eventId) {
        return eventStaffRepository.countStaffByEventId(eventId);
    }
}
