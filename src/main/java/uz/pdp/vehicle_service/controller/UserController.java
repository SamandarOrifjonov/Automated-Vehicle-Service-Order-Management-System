package uz.pdp.vehicle_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vehicle_service.entity.User;
import uz.pdp.vehicle_service.repository.UserRepository;
import uz.pdp.vehicle_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // ADMIN / CUSTOMER: register customer
    @PostMapping("/customers")
    public User createCustomer(@RequestBody User user) {
        return userService.createCustomer(user);
    }

    // ADMIN: list all users
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
