package ru.vsu.sheluhin.carService.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.sheluhin.carService.request.RegistrationRequest;
//import ru.vsu.sheluhin.carService.service.AuthService;
//
//@RestController
//@RequestMapping(path = "/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping(path = "/registration")
//    public ResponseEntity<Void> registration(@RequestBody RegistrationRequest request) {
//        authService.registration(request);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//}
