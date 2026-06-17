package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.User;
import ru.vsu.sheluhin.carService.repository.UserRepository;
import ru.vsu.sheluhin.carService.response.PageUserResponse;
import ru.vsu.sheluhin.carService.response.UserResponse;
import ru.vsu.sheluhin.carService.response.UserStatisticsResponse;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CommonProperties commonProperties;

    public UserService(UserRepository userRepository, CommonProperties commonProperties) {
        this.userRepository = userRepository;
        this.commonProperties = commonProperties;
    }

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findAuthUsersByPhoneNumber(login);
    }

    public PageUserResponse getUsers(Optional<User.UserType> userType, Integer page) {
        Pageable pageable = PageRequest.of(page, commonProperties.getPageSize());

        return userType.map(type ->
                PageUserResponse.from(userRepository.findAuthUsersByUserType(type, pageable)))
                .orElseGet(() -> PageUserResponse.from(userRepository.findAllBy(pageable)));
    }

    public UserStatisticsResponse getUserStatistics(int userId) {
        return userRepository.getAuthUserStatistics(userId);
    }

    public Optional<UserResponse> findUserByPhone(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    public User addUser(User newMaster) {
        return userRepository.save(newMaster);
    }

    @Transactional
    public void setWorkStatus(int userId, User.WorkStatus newWorkStatus) {
        userRepository.updateWorkStatusById(newWorkStatus, userId);
    }

    @Transactional
    public void setUserType(int userId, User.UserType newUserType) {
        userRepository.updateUserTypeById(newUserType, userId);
        if(newUserType.equals(User.UserType.CLIENT))
            userRepository.updateWorkStatusById(null, userId);
    }
}
