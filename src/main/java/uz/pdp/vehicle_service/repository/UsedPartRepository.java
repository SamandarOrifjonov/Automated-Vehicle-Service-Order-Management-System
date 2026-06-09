package uz.pdp.vehicle_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vehicle_service.entity.UsedPart;

import java.util.List;

public interface UsedPartRepository extends JpaRepository<UsedPart, Long> {
    List<UsedPart> findByServiceOrderId(Long orderId);
}
