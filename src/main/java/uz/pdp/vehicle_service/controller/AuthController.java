package uz.pdp.vehicle_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vehicle_service.dto.LoginRequest;
import uz.pdp.vehicle_service.dto.LoginResponse;
import uz.pdp.vehicle_service.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return userService.authenticate(req.getEmail(), req.getPassword())
                .map(u -> ResponseEntity.ok(
                        (Object) new LoginResponse(u.getId(), u.getFullName(), u.getRole().name())
                ))
                .orElseGet(() -> ResponseEntity.status(401).body("Invalid credentials"));
    }
}
