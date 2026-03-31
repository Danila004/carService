package ru.vsu.sheluhin.carService.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.entity.AuthUser;
import ru.vsu.sheluhin.carService.exeption.ValidationException;
import ru.vsu.sheluhin.carService.repository.AuthUserRepository;
import ru.vsu.sheluhin.carService.request.RegistrationRequest;
import ru.vsu.sheluhin.carService.response.ErrorCode;

import java.util.Optional;
//
//@Service
//public class AuthService {
//
//    private final AuthUserRepository authUserRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public AuthService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
//        this.authUserRepository = authUserRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Transactional
//    public void registration(RegistrationRequest request) {
//        authUserRepository.lockOnLogin(request.PhoneNumber());
//        Optional<AuthUser> authUser = authUserRepository.findAuthUsersByPhoneNumber(request.PhoneNumber());
//        if (authUser.isPresent()) {
//            throw new ValidationException(ErrorCode.ALREADY_EXISTS);
//        }
//        AuthUser newAuthUser = new AuthUser();
//        newAuthUser.setUsername(request.username());
//        newAuthUser.setSurname(request.surname());
//        newAuthUser.setPhoneNumber(request.PhoneNumber());
//        newAuthUser.setPasswordHash(passwordEncoder.encode(request.password()));
//        newAuthUser.setUserType(request.userType());
//        if (request.userType() != null) {
//            newAuthUser.setWorkStatus(AuthUser.WorkStatus.WORK);
//        }
//        authUserRepository.save(newAuthUser);
//    }
//}
