package uz.pdp.vehicle_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vehicle_service.dto.AddUsedPartRequest;
import uz.pdp.vehicle_service.dto.AssignMechanicRequest;
import uz.pdp.vehicle_service.dto.CreateOrderRequest;
import uz.pdp.vehicle_service.entity.*;
import uz.pdp.vehicle_service.service.ServiceOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceOrderController {

    private final ServiceOrderService orderService;

    // CUSTOMER: create order
    @PostMapping
    public ServiceOrder createOrder(@RequestBody CreateOrderRequest req) {
        return orderService.createOrder(req);
    }

    // CUSTOMER: own orders
    @GetMapping("/customer/{customerId}")
    public List<ServiceOrder> getByCustomer(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }

    // MANAGER/ADMIN: all orders
    @GetMapping
    public List<ServiceOrder> getAll() {
        return orderService.getAllOrders();
    }

    // MANAGER: approve / reject / complete → status o'zgartirish
    @PutMapping("/{orderId}/status")
    public ServiceOrder updateStatus(@PathVariable Long orderId,
                                     @RequestParam ServiceOrderStatus status) {
        return orderService.updateStatus(orderId, status);
    }

    // MANAGER: assign mechanic to order
    @PostMapping("/{orderId}/assign")
    public ServiceOrderAssignment assignMechanic(@PathVariable Long orderId,
                                                 @RequestBody AssignMechanicRequest req) {
        return orderService.assignMechanic(orderId, req.getMechanicId());
    }

    // MECHANIC: my assigned orders
    @GetMapping("/mechanic/{mechanicId}")
    public List<ServiceOrder> getForMechanic(@PathVariable Long mechanicId) {
        return orderService.getOrdersForMechanic(mechanicId);
    }

    // MECHANIC: add used part → totalPrice auto-updates
    @PostMapping("/parts")
    public UsedPart addPart(@RequestBody AddUsedPartRequest req) {
        return orderService.addUsedPart(req);
    }

    // MANAGER: generate invoice after order completed
    @PostMapping("/{orderId}/invoice")
    public Invoice generateInvoice(@PathVariable Long orderId) {
        return orderService.generateInvoice(orderId);
    }
}
