package ru.vsu.sheluhin.carService.service;

//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        Optional<User> opUser = userRepository.findAuthUsersByPhoneNumber(username);
//        if (opUser.isEmpty())
//            throw new ValidationException(ErrorCode.NOT_FOUND);
//        return User.builder()
//                .username(opUser.get().getPhoneNumber())
//                .password(opUser.get().getPasswordHash())
//                .roles(new String[0])
//                .build();
//    }
//}
