package ru.vsu.sheluhin.carService.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vsu.sheluhin.carService.entity.AuthUser;
import ru.vsu.sheluhin.carService.exeption.ValidationException;
import ru.vsu.sheluhin.carService.repository.AuthUserRepository;
import ru.vsu.sheluhin.carService.response.ErrorCode;

import java.util.Optional;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final AuthUserRepository userRepository;
//
//    public CustomUserDetailsService(AuthUserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        Optional<AuthUser> opUser = userRepository.findAuthUsersByPhoneNumber(username);
//        if (opUser.isEmpty())
//            throw new ValidationException(ErrorCode.NOT_FOUND);
//        return User.builder()
//                .username(opUser.get().getPhoneNumber())
//                .password(opUser.get().getPasswordHash())
//                .roles(new String[0])
//                .build();
//    }
//}
