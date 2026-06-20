package ru.vsu.sheluhin.carService.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.request.RegistrationRequest;
import ru.vsu.sheluhin.carService.response.UserResponse;
import ru.vsu.sheluhin.carService.service.AuthService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/signup")
    public UserResponse registration(@Valid @RequestBody RegistrationRequest request) {
        return authService.registration(request);
    }
}
