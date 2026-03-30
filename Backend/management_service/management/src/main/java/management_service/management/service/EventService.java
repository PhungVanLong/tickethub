package management_service.management.service;

import lombok.RequiredArgsConstructor;
import management_service.management.entity.Event;
import management_service.management.entity.User;
import management_service.management.entity.enums.EventStatus;
import management_service.management.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    
    private final EventRepository eventRepository;
    private final UserService userService;
    
    public Event createEvent(Event event, Long organizerId) {
        User organizer = userService.getUserById(organizerId)
            .orElseThrow(() -> new RuntimeException("Organizer not found with id: " + organizerId));
        
        event.setOrganizer(organizer);
        event.setStatus(EventStatus.DRAFT);
        event.setIsPublished(false);
        
        return eventRepository.save(event);
    }
    
    @Transactional(readOnly = true)
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Event> getEventsByOrganizer(Long organizerId) {
        User organizer = userService.getUserById(organizerId)
            .orElseThrow(() -> new RuntimeException("Organizer not found with id: " + organizerId));
        return eventRepository.findByOrganizer(organizer);
    }
    
    @Transactional(readOnly = true)
    public List<Event> getEventsByStatus(EventStatus status) {
        return eventRepository.findByStatus(status);
    }
    
    @Transactional(readOnly = true)
    public List<Event> getEventsByCity(String city) {
        return eventRepository.findByCity(city);
    }
    
    @Transactional(readOnly = true)
    public List<Event> getPublishedEvents() {
        return eventRepository.findPublishedEvents();
    }
    
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setVenue(eventDetails.getVenue());
        event.setCity(eventDetails.getCity());
        event.setLocationCoords(eventDetails.getLocationCoords());
        event.setStartTime(eventDetails.getStartTime());
        event.setEndTime(eventDetails.getEndTime());
        event.setBannerUrl(eventDetails.getBannerUrl());
        event.setIsPublished(eventDetails.getIsPublished());
        
        return eventRepository.save(event);
    }
    
    public Event updateEventStatus(Long id, EventStatus status) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        
        event.setStatus(status);
        return eventRepository.save(event);
    }
    
    public void publishEvent(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        
        if (event.getStatus() != EventStatus.APPROVED) {
            throw new RuntimeException("Event must be approved before publishing");
        }
        
        event.setIsPublished(true);
        eventRepository.save(event);
    }
    
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Event> searchEventsByTitle(String title) {
        return eventRepository.searchByTitle(title);
    }
    
    @Transactional(readOnly = true)
    public List<Event> getEventsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findEventsBetweenDates(start, end);
    }
}
