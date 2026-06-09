package uz.pdp.vehicle_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vehicle_service.entity.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
}
