package ru.vsu.sheluhin.carService.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.entity.User;
import ru.vsu.sheluhin.carService.exeption.ValidationException;
import ru.vsu.sheluhin.carService.repository.UserRepository;
import ru.vsu.sheluhin.carService.request.LoginRequest;
import ru.vsu.sheluhin.carService.request.RegistrationRequest;
import ru.vsu.sheluhin.carService.response.UserResponse;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public ResponseEntity<?> registration(RegistrationRequest request, HttpServletResponse response) {
        userRepository.lockOnLogin(request.phoneNumber());
        Optional<User> user = userRepository.findUserByPhoneNumber(request.phoneNumber());
        if (user.isPresent()) {
            throw new ValidationException("Пользователь с таким номером уже существует");
        }
        User newUser = userRepository.save(new User(0,
                            request.userName(),
                            request.phoneNumber(),
                            passwordEncoder.encode(request.password()),
                            User.UserType.CLIENT,
                            null));

        String accessToken = jwtService.generateToken(newUser);
        String refreshToken = jwtService.generateToken(newUser);
        jwtService.createRefreshToken(request.phoneNumber(), refreshToken);

        Cookie cookie = new Cookie("token", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);

        return ResponseEntity.ok().body(new UserResponse(newUser.getUserId(),
                newUser.getUserName(),
                newUser.getPhoneNumber(),
                newUser.getUserType(),
                newUser.getWorkStatus()));
    }

    public UserResponse login(LoginRequest request, HttpServletResponse response) {
        System.out.println("ok");
        Optional<User> user = userRepository.findUserByPhoneNumber(request.phoneNumber());
        if (user.isEmpty()) {
            throw new ValidationException("Неверный логин или пароль");
        }

        if(!passwordEncoder.matches(request.password(), user.get().getPasswordHash())) {
            throw new ValidationException("Неверный логин или пароль");
        }

        String accessToken = jwtService.generateToken(user.get());
        String refreshToken = jwtService.generateToken(user.get());
        jwtService.deleteRefreshTokenByPhoneNumber(request.phoneNumber());
        jwtService.createRefreshToken(request.phoneNumber(), refreshToken);

        Cookie cookie = new Cookie("token", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);

        return new UserResponse(user.get().getUserId(),
                user.get().getUserName(),
                user.get().getPhoneNumber(),
                user.get().getUserType(),
                user.get().getWorkStatus());
    }
}
