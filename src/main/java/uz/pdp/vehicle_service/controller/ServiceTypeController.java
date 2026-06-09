package uz.pdp.vehicle_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vehicle_service.entity.ServiceType;
import uz.pdp.vehicle_service.repository.ServiceTypeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/service-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceTypeController {

    private final ServiceTypeRepository repo;

    // All roles: list service types
    @GetMapping
    public List<ServiceType> getAll() {
        return repo.findAll();
    }

    // ADMIN: create service type
    @PostMapping
    public ServiceType create(@RequestBody ServiceType type) {
        return repo.save(type);
    }

    // ADMIN: delete service type
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
