package uz.pdp.vehicle_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vehicle_service.entity.ServiceOrderAssignment;

import java.util.List;
import java.util.Optional;

public interface ServiceOrderAssignmentRepository extends JpaRepository<ServiceOrderAssignment, Long> {
    Optional<ServiceOrderAssignment> findByServiceOrderId(Long orderId);
    List<ServiceOrderAssignment> findByMechanicId(Long mechanicId);
}
