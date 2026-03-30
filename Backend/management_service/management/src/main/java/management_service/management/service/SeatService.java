package management_service.management.service;

import lombok.RequiredArgsConstructor;
import management_service.management.entity.Seat;
import management_service.management.entity.SeatMap;
import management_service.management.entity.TicketTier;
import management_service.management.entity.enums.SeatStatus;
import management_service.management.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatService {
    
    private final SeatRepository seatRepository;
    private final SeatMapService seatMapService;
    private final TicketTierService ticketTierService;
    
    public Seat createSeat(Seat seat, Long seatMapId, Long ticketTierId) {
        SeatMap seatMap = seatMapService.getSeatMapById(seatMapId)
            .orElseThrow(() -> new RuntimeException("SeatMap not found with id: " + seatMapId));
        
        TicketTier ticketTier = null;
        if (ticketTierId != null) {
            ticketTier = ticketTierService.getTicketTierById(ticketTierId)
                .orElseThrow(() -> new RuntimeException("TicketTier not found with id: " + ticketTierId));
        }
        
        seat.setSeatMap(seatMap);
        seat.setTicketTier(ticketTier);
        seat.setStatus(SeatStatus.AVAILABLE);
        
        if (seatRepository.findBySeatCode(seat.getSeatCode()).isPresent()) {
            throw new RuntimeException("Seat code already exists: " + seat.getSeatCode());
        }
        
        return seatRepository.save(seat);
    }
    
    @Transactional(readOnly = true)
    public Optional<Seat> getSeatById(Long id) {
        return seatRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<Seat> getSeatByCode(String seatCode) {
        return seatRepository.findBySeatCode(seatCode);
    }
    
    @Transactional(readOnly = true)
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Seat> getSeatsBySeatMap(Long seatMapId) {
        SeatMap seatMap = seatMapService.getSeatMapById(seatMapId)
            .orElseThrow(() -> new RuntimeException("SeatMap not found with id: " + seatMapId));
        return seatRepository.findBySeatMap(seatMap);
    }
    
    @Transactional(readOnly = true)
    public List<Seat> getSeatsByTicketTier(Long ticketTierId) {
        TicketTier ticketTier = ticketTierService.getTicketTierById(ticketTierId)
            .orElseThrow(() -> new RuntimeException("TicketTier not found with id: " + ticketTierId));
        return seatRepository.findByTicketTier(ticketTier);
    }
    
    @Transactional(readOnly = true)
    public List<Seat> getSeatsByStatus(SeatStatus status) {
        return seatRepository.findByStatus(status);
    }
    
    @Transactional(readOnly = true)
    public List<Seat> getAvailableSeatsBySeatMap(Long seatMapId) {
        return seatRepository.findBySeatMapIdAndStatus(seatMapId, SeatStatus.AVAILABLE);
    }
    
    public Seat updateSeat(Long id, Seat seatDetails) {
        Seat seat = seatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id));
        
        if (!seat.getSeatCode().equals(seatDetails.getSeatCode()) && 
            seatRepository.findBySeatCode(seatDetails.getSeatCode()).isPresent()) {
            throw new RuntimeException("Seat code already exists: " + seatDetails.getSeatCode());
        }
        
        seat.setRowLabel(seatDetails.getRowLabel());
        seat.setColNumber(seatDetails.getColNumber());
        seat.setSeatCode(seatDetails.getSeatCode());
        seat.setStatus(seatDetails.getStatus());
        
        return seatRepository.save(seat);
    }
    
    public Seat updateSeatStatus(Long id, SeatStatus status) {
        Seat seat = seatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id));
        
        seat.setStatus(status);
        return seatRepository.save(seat);
    }
    
    public Seat assignTicketTier(Long seatId, Long ticketTierId) {
        Seat seat = seatRepository.findById(seatId)
            .orElseThrow(() -> new RuntimeException("Seat not found with id: " + seatId));
        
        TicketTier ticketTier = ticketTierService.getTicketTierById(ticketTierId)
            .orElseThrow(() -> new RuntimeException("TicketTier not found with id: " + ticketTierId));
        
        seat.setTicketTier(ticketTier);
        return seatRepository.save(seat);
    }
    
    public void deleteSeat(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new RuntimeException("Seat not found with id: " + id);
        }
        seatRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long getAvailableSeatCountBySeatMap(Long seatMapId) {
        return seatRepository.countAvailableSeatsBySeatMapId(seatMapId);
    }
    
    @Transactional(readOnly = true)
    public Optional<Seat> getSeatByPosition(String rowLabel, Integer colNumber, Long seatMapId) {
        return seatRepository.findByPosition(rowLabel, colNumber, seatMapId);
    }
}
