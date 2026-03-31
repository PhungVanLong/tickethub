package booking_service.booking.repository;

import booking_service.booking.entity.Promotion;
import booking_service.booking.entity.enums.PromotionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, UUID> {
    
    Optional<Promotion> findByCode(String code);
    
    List<Promotion> findByStatus(PromotionStatus status);
    
    @Query("SELECT p FROM Promotion p WHERE p.code = :code AND p.status = :status AND p.startDate <= :now AND p.endDate >= :now")
    Optional<Promotion> findActivePromotionByCode(@Param("code") String code, @Param("status") PromotionStatus status, @Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Promotion p WHERE p.startDate <= :now AND p.endDate >= :now AND p.status = :status")
    List<Promotion> findActivePromotions(@Param("now") LocalDateTime now, @Param("status") PromotionStatus status);
    
    @Query("SELECT p FROM Promotion p WHERE p.endDate < :now AND p.status = :status")
    List<Promotion> findExpiredPromotions(@Param("now") LocalDateTime now, @Param("status") PromotionStatus status);
    
    boolean existsByCode(String code);
}
