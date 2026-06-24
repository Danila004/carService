package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.User;
import ru.vsu.sheluhin.carService.repository.DateSlotRepository;
import ru.vsu.sheluhin.carService.repository.UserRepository;
import ru.vsu.sheluhin.carService.response.PageUserResponse;
import ru.vsu.sheluhin.carService.response.UserResponse;
import ru.vsu.sheluhin.carService.response.UserStatisticsResponse;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CommonProperties commonProperties;
    private final DateSlotService dateSlotService;
    private final DateSlotRepository dateSlotRepository;

    public UserService(UserRepository userRepository, CommonProperties commonProperties, DateSlotService dateSlotService, DateSlotRepository dateSlotRepository) {
        this.userRepository = userRepository;
        this.commonProperties = commonProperties;
        this.dateSlotService = dateSlotService;
        this.dateSlotRepository = dateSlotRepository;
    }

    public PageUserResponse getUsers(Optional<User.UserType> userType, Integer page) {
        Pageable pageable = PageRequest.of(page, commonProperties.getPageSize());

        return userType.map(type ->
                PageUserResponse.from(userRepository.findAuthUsersByUserType(type, pageable)))
                .orElse(PageUserResponse.from(userRepository.findAllBy(pageable)));
    }

    public UserStatisticsResponse getUserStatistics(int userId) {
        return userRepository.getAuthUserStatistics(userId);
    }

    public Optional<UserResponse> findUserByPhone(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber).map(user ->
                new UserResponse(user.getUserId(),
                        user.getUserName(),
                        user.getPhoneNumber(),
                        user.getUserType(),
                        user.getWorkStatus()));
    }

    public User addUser(User newMaster) {
        return userRepository.save(newMaster);
    }

    @Transactional
    public void setWorkStatus(int userId, User.WorkStatus newWorkStatus) {
        User user = userRepository.getUserByUserId(userId);
        userRepository.updateWorkStatusById(newWorkStatus, userId);
        if(userRepository.getUserByUserId(userId).getUserType().equals(User.UserType.MASTER)) {
            if(newWorkStatus.equals(User.WorkStatus.SICK))
                dateSlotRepository.bookAllByVisitDateBetweenAndMasterId(LocalDate.now().minusDays(1),
                        LocalDate.now().plusDays(7),
                        userId);
            if(newWorkStatus.equals(User.WorkStatus.NOT_WORK))
                dateSlotRepository.deleteAllByVisitDateAfterAndMasterId(LocalDate.now().plusDays(14), userId);
            if(newWorkStatus.equals(User.WorkStatus.WORK) && user.getWorkStatus().equals(User.WorkStatus.NOT_WORK))
                dateSlotService.createSlotsForNewMaster(userId);
        }
    }

    @Transactional
    public void setUserType(int userId, User.UserType newUserType) {
        User user = userRepository.getUserByUserId(userId);
        userRepository.updateUserTypeById(newUserType, userId);
        if(user.getUserType().equals(User.UserType.MASTER))
            dateSlotRepository.deleteAllByMasterId(userId);
        if(newUserType.equals(User.UserType.CLIENT))
            userRepository.updateWorkStatusById(null, userId);
        if(newUserType.equals(User.UserType.MASTER))
            dateSlotService.createSlotsForNewMaster(userId);
    }
}
