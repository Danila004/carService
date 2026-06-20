package ru.vsu.sheluhin.carService.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.entity.User;
import ru.vsu.sheluhin.carService.exeption.ValidationException;
import ru.vsu.sheluhin.carService.repository.UserRepository;
import ru.vsu.sheluhin.carService.request.RegistrationRequest;
import ru.vsu.sheluhin.carService.response.UserResponse;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse registration(RegistrationRequest request) {
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

        return new UserResponse(newUser.getUserId(),
                newUser.getUserName(),
                newUser.getPhoneNumber(),
                newUser.getUserType(),
                newUser.getWorkStatus());
    }
}
