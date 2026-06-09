package uz.pdp.vehicle_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vehicle_service.entity.Invoice;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByServiceOrderId(Long orderId);
}
