package management_service.management.service;

import lombok.RequiredArgsConstructor;
import management_service.management.entity.SeatMap;
import management_service.management.repository.SeatMapRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatMapService {
    
    private final SeatMapRepository seatMapRepository;
    private final EventService eventService;
    
    public SeatMap createSeatMap(SeatMap seatMap, Long eventId) {
        var event = eventService.getEventById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        
        seatMap.setEvent(event);
        return seatMapRepository.save(seatMap);
    }
    
    @Transactional(readOnly = true)
    public Optional<SeatMap> getSeatMapById(Long id) {
        return seatMapRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<SeatMap> getAllSeatMaps() {
        return seatMapRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<SeatMap> getSeatMapsByEvent(Long eventId) {
        return seatMapRepository.findByEventId(eventId);
    }
    
    public SeatMap updateSeatMap(Long id, SeatMap seatMapDetails) {
        SeatMap seatMap = seatMapRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("SeatMap not found with id: " + id));
        
        seatMap.setName(seatMapDetails.getName());
        seatMap.setLayoutJson(seatMapDetails.getLayoutJson());
        seatMap.setTotalRows(seatMapDetails.getTotalRows());
        seatMap.setTotalCols(seatMapDetails.getTotalCols());
        
        return seatMapRepository.save(seatMap);
    }
    
    public void deleteSeatMap(Long id) {
        if (!seatMapRepository.existsById(id)) {
            throw new RuntimeException("SeatMap not found with id: " + id);
        }
        seatMapRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long getSeatMapCountByEvent(Long eventId) {
        return seatMapRepository.countByEventId(eventId);
    }
}
