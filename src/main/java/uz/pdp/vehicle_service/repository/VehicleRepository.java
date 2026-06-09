package uz.pdp.vehicle_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vehicle_service.entity.Vehicle;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByCustomerId(Long customerId);
}
