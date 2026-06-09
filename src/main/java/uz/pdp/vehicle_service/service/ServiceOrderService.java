package uz.pdp.vehicle_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uz.pdp.vehicle_service.dto.AddUsedPartRequest;
import uz.pdp.vehicle_service.dto.CreateOrderRequest;
import uz.pdp.vehicle_service.entity.Invoice;
import uz.pdp.vehicle_service.entity.InvoiceStatus;
import uz.pdp.vehicle_service.entity.ServiceOrder;
import uz.pdp.vehicle_service.entity.ServiceOrderAssignment;
import uz.pdp.vehicle_service.entity.ServiceOrderStatus;
import uz.pdp.vehicle_service.entity.ServiceType;
import uz.pdp.vehicle_service.entity.UsedPart;
import uz.pdp.vehicle_service.entity.User;
import uz.pdp.vehicle_service.entity.Vehicle;
import uz.pdp.vehicle_service.repository.InvoiceRepository;
import uz.pdp.vehicle_service.repository.ServiceOrderAssignmentRepository;
import uz.pdp.vehicle_service.repository.ServiceOrderRepository;
import uz.pdp.vehicle_service.repository.ServiceTypeRepository;
import uz.pdp.vehicle_service.repository.UsedPartRepository;
import uz.pdp.vehicle_service.repository.UserRepository;
import uz.pdp.vehicle_service.repository.VehicleRepository;

@Service
@RequiredArgsConstructor
public class ServiceOrderService {

    private final ServiceOrderRepository orderRepo;
    private final UserRepository userRepo;
    private final VehicleRepository vehicleRepo;
    private final ServiceTypeRepository typeRepo;
    private final ServiceOrderAssignmentRepository assignmentRepo;
    private final UsedPartRepository usedPartRepo;
    private final InvoiceRepository invoiceRepo;
    private final NotificationService notificationService;

    public ServiceOrder createOrder(CreateOrderRequest req) {
        User customer = userRepo.findById(req.getCustomerId()).orElseThrow();
        Vehicle vehicle = vehicleRepo.findById(req.getVehicleId()).orElseThrow();
        ServiceType type = typeRepo.findById(req.getServiceTypeId()).orElseThrow();

        ServiceOrder order = new ServiceOrder();
        order.setCustomer(customer);
        order.setVehicle(vehicle);
        order.setServiceType(type);
        order.setCreatedAt(LocalDateTime.now());
        order.setScheduledDate(req.getScheduledDate());
        order.setStatus(ServiceOrderStatus.PENDING);
        order.setDescription(req.getDescription());
        order.setTotalPrice(type.getBasePrice());

        ServiceOrder saved = orderRepo.save(order);
        notificationService.sendStatusChangeNotification(customer, saved);
        return saved;
    }

    public List<ServiceOrder> getOrdersByCustomer(Long customerId) {
        return orderRepo.findByCustomerId(customerId);
    }

    public List<ServiceOrder> getAllOrders() {
        return orderRepo.findAll();
    }

    public ServiceOrder updateStatus(Long orderId, ServiceOrderStatus status) {
        ServiceOrder order = orderRepo.findById(orderId).orElseThrow();
        order.setStatus(status);
        ServiceOrder saved = orderRepo.save(order);
        notificationService.sendStatusChangeNotification(order.getCustomer(), saved);
        return saved;
    }

    public ServiceOrderAssignment assignMechanic(Long orderId, Long mechanicId) {
        ServiceOrder order = orderRepo.findById(orderId).orElseThrow();
        User mechanic = userRepo.findById(mechanicId).orElseThrow();

        ServiceOrderAssignment assignment = assignmentRepo
                .findByServiceOrderId(orderId)
                .orElse(new ServiceOrderAssignment());

        assignment.setServiceOrder(order);
        assignment.setMechanic(mechanic);
        assignment.setAssignedAt(LocalDateTime.now());

        return assignmentRepo.save(assignment);
    }

    public UsedPart addUsedPart(AddUsedPartRequest req) {
        ServiceOrder order = orderRepo.findById(req.getOrderId()).orElseThrow();

        UsedPart part = new UsedPart();
        part.setServiceOrder(order);
        part.setPartName(req.getPartName());
        part.setQuantity(req.getQuantity());
        part.setUnitPrice(req.getUnitPrice());

        UsedPart saved = usedPartRepo.save(part);

        // update totalPrice
        BigDecimal partTotal = req.getUnitPrice().multiply(BigDecimal.valueOf(req.getQuantity()));
        BigDecimal currentTotal = order.getTotalPrice() != null ? order.getTotalPrice() : BigDecimal.ZERO;
        order.setTotalPrice(currentTotal.add(partTotal));
        orderRepo.save(order);

        return saved;
    }

    public Invoice generateInvoice(Long orderId) {
        ServiceOrder order = orderRepo.findById(orderId).orElseThrow();

        if (invoiceRepo.findByServiceOrderId(orderId).isPresent()) {
            throw new IllegalStateException("Invoice already exists for order #" + orderId);
        }

        Invoice invoice = new Invoice();
        invoice.setServiceOrder(order);
        invoice.setInvoiceNumber("INV-" + order.getId());
        invoice.setAmount(order.getTotalPrice());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setStatus(InvoiceStatus.UNPAID);

        return invoiceRepo.save(invoice);
    }

    public List<ServiceOrder> getOrdersForMechanic(Long mechanicId) {
        return assignmentRepo.findByMechanicId(mechanicId)
                .stream()
                .map(ServiceOrderAssignment::getServiceOrder)
                .toList();
    }
}
