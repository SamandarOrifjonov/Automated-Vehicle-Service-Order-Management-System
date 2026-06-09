package uz.pdp.vehicle_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vehicle_service.entity.ServiceOrder;
import uz.pdp.vehicle_service.entity.ServiceOrderStatus;

import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    List<ServiceOrder> findByCustomerId(Long customerId);
    List<ServiceOrder> findByStatus(ServiceOrderStatus status);
}
