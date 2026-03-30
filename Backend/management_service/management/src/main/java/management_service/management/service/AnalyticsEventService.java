package management_service.management.service;

import lombok.RequiredArgsConstructor;
import management_service.management.entity.AnalyticsEvent;
import management_service.management.entity.Event;
import management_service.management.repository.AnalyticsEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalyticsEventService {
    
    private final AnalyticsEventRepository analyticsEventRepository;
    private final EventService eventService;
    
    public AnalyticsEvent createAnalyticsSnapshot(Long eventId, Integer totalTicketsSold, 
                                                  Integer totalCheckins, BigDecimal totalRevenue, String tierBreakdown) {
        Event event = eventService.getEventById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        
        AnalyticsEvent analytics = AnalyticsEvent.builder()
            .event(event)
            .totalTicketsSold(totalTicketsSold)
            .totalCheckins(totalCheckins)
            .totalRevenue(totalRevenue)
            .tierBreakdown(tierBreakdown)
            .snapshotAt(LocalDateTime.now())
            .build();
        
        return analyticsEventRepository.save(analytics);
    }
    
    @Transactional(readOnly = true)
    public Optional<AnalyticsEvent> getAnalyticsById(Long id) {
        return analyticsEventRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<AnalyticsEvent> getAnalyticsByEvent(Long eventId) {
        return analyticsEventRepository.findByEventIdOrderBySnapshotAtDesc(eventId);
    }
    
    @Transactional(readOnly = true)
    public AnalyticsEvent getLatestAnalyticsByEvent(Long eventId) {
        return analyticsEventRepository.findLatestByEventId(eventId);
    }
    
    @Transactional(readOnly = true)
    public List<AnalyticsEvent> getAnalyticsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return analyticsEventRepository.findAnalyticsBetweenDates(start, end);
    }
    
    public AnalyticsEvent updateAnalytics(Long id, Integer totalTicketsSold, Integer totalCheckins, 
                                         BigDecimal totalRevenue, String tierBreakdown) {
        AnalyticsEvent analytics = analyticsEventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Analytics not found with id: " + id));
        
        analytics.setTotalTicketsSold(totalTicketsSold);
        analytics.setTotalCheckins(totalCheckins);
        analytics.setTotalRevenue(totalRevenue);
        analytics.setTierBreakdown(tierBreakdown);
        analytics.setSnapshotAt(LocalDateTime.now());
        
        return analyticsEventRepository.save(analytics);
    }
    
    public void deleteAnalytics(Long id) {
        if (!analyticsEventRepository.existsById(id)) {
            throw new RuntimeException("Analytics not found with id: " + id);
        }
        analyticsEventRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long getAnalyticsCountByEvent(Long eventId) {
        return analyticsEventRepository.countByEventId(eventId);
    }
    
    @Transactional(readOnly = true)
    public List<AnalyticsEvent> getAllAnalytics() {
        return analyticsEventRepository.findAll();
    }
}
