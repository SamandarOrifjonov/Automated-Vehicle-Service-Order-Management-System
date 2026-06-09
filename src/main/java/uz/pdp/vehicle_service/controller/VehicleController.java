package uz.pdp.vehicle_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vehicle_service.entity.User;
import uz.pdp.vehicle_service.entity.Vehicle;
import uz.pdp.vehicle_service.repository.UserRepository;
import uz.pdp.vehicle_service.repository.VehicleRepository;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {

    private final VehicleRepository vehicleRepo;
    private final UserRepository userRepo;

    // CUSTOMER: add vehicle
    @PostMapping
    public Vehicle createVehicle(@RequestParam Long customerId,
                                 @RequestBody Vehicle vehicle) {
        User customer = userRepo.findById(customerId).orElseThrow();
        vehicle.setCustomer(customer);
        return vehicleRepo.save(vehicle);
    }

    // CUSTOMER: own vehicles
    @GetMapping("/customer/{customerId}")
    public List<Vehicle> getByCustomer(@PathVariable Long customerId) {
        return vehicleRepo.findByCustomerId(customerId);
    }

    // ADMIN/MANAGER: all vehicles
    @GetMapping
    public List<Vehicle> getAll() {
        return vehicleRepo.findAll();
    }
}
