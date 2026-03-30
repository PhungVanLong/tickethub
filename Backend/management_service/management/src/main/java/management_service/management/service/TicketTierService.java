package management_service.management.service;

import lombok.RequiredArgsConstructor;
import management_service.management.entity.TicketTier;
import management_service.management.entity.enums.TierType;
import management_service.management.repository.TicketTierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketTierService {
    
    private final TicketTierRepository ticketTierRepository;
    private final SeatMapService seatMapService;
    
    public TicketTier createTicketTier(TicketTier ticketTier, Long seatMapId) {
        var seatMap = seatMapService.getSeatMapById(seatMapId)
            .orElseThrow(() -> new RuntimeException("SeatMap not found with id: " + seatMapId));
        
        ticketTier.setSeatMap(seatMap);
        ticketTier.setQuantityAvailable(ticketTier.getQuantityTotal());
        return ticketTierRepository.save(ticketTier);
    }
    
    @Transactional(readOnly = true)
    public Optional<TicketTier> getTicketTierById(Long id) {
        return ticketTierRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<TicketTier> getAllTicketTiers() {
        return ticketTierRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<TicketTier> getTicketTiersBySeatMap(Long seatMapId) {
        return ticketTierRepository.findBySeatMapId(seatMapId);
    }
    
    @Transactional(readOnly = true)
    public List<TicketTier> getTicketTiersByType(TierType tierType) {
        return ticketTierRepository.findByTierType(tierType);
    }
    
    @Transactional(readOnly = true)
    public List<TicketTier> getTicketTiersBySeatMapAndType(Long seatMapId, TierType tierType) {
        return ticketTierRepository.findBySeatMapIdAndTierType(seatMapId, tierType);
    }
    
    public TicketTier updateTicketTier(Long id, TicketTier ticketTierDetails) {
        TicketTier ticketTier = ticketTierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TicketTier not found with id: " + id));
        
        ticketTier.setName(ticketTierDetails.getName());
        ticketTier.setTierType(ticketTierDetails.getTierType());
        ticketTier.setPrice(ticketTierDetails.getPrice());
        ticketTier.setQuantityTotal(ticketTierDetails.getQuantityTotal());
        ticketTier.setColorCode(ticketTierDetails.getColorCode());
        ticketTier.setSaleStart(ticketTierDetails.getSaleStart());
        ticketTier.setSaleEnd(ticketTierDetails.getSaleEnd());
        
        return ticketTierRepository.save(ticketTier);
    }
    
    public void deleteTicketTier(Long id) {
        if (!ticketTierRepository.existsById(id)) {
            throw new RuntimeException("TicketTier not found with id: " + id);
        }
        ticketTierRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long getTicketTierCountBySeatMap(Long seatMapId) {
        return ticketTierRepository.countBySeatMapId(seatMapId);
    }
}
