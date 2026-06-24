package ru.vsu.sheluhin.carService.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.vsu.sheluhin.carService.entity.User;
import ru.vsu.sheluhin.carService.exeption.ValidationException;
import ru.vsu.sheluhin.carService.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {
        Optional<User> opUser = userRepository.findUserByPhoneNumber(phoneNumber);
        if (opUser.isEmpty())
            throw new ValidationException("NOT_FOUND");
        return org.springframework.security.core.userdetails.User.builder()
                .username(opUser.get().getPhoneNumber())
                .password(opUser.get().getPasswordHash())
                .roles(opUser.get().getUserType().toString())
                .build();
    }
}
