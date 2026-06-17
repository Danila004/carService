package ru.vsu.sheluhin.carService.service;

//
//@Service
//public class AuthService {
//
//    private final UserRepository authUserRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public AuthService(UserRepository authUserRepository, PasswordEncoder passwordEncoder) {
//        this.authUserRepository = authUserRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Transactional
//    public void registration(RegistrationRequest request) {
//        authUserRepository.lockOnLogin(request.PhoneNumber());
//        Optional<User> authUser = authUserRepository.findAuthUsersByPhoneNumber(request.PhoneNumber());
//        if (authUser.isPresent()) {
//            throw new ValidationException(ErrorCode.ALREADY_EXISTS);
//        }
//        User newAuthUser = new User();
//        newAuthUser.setUsername(request.username());
//        newAuthUser.setSurname(request.surname());
//        newAuthUser.setPhoneNumber(request.PhoneNumber());
//        newAuthUser.setPasswordHash(passwordEncoder.encode(request.password()));
//        newAuthUser.setUserType(request.userType());
//        if (request.userType() != null) {
//            newAuthUser.setWorkStatus(User.WorkStatus.WORK);
//        }
//        authUserRepository.save(newAuthUser);
//    }
//}
