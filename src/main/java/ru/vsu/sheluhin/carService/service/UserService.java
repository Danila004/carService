package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.AuthUser;
import ru.vsu.sheluhin.carService.entity.UnauthUser;
import ru.vsu.sheluhin.carService.repository.AuthUserRepository;
import ru.vsu.sheluhin.carService.repository.UnauthUserRepository;
import ru.vsu.sheluhin.carService.response.PageUserResponse;
import ru.vsu.sheluhin.carService.response.UserResponse;
import ru.vsu.sheluhin.carService.response.UserStatisticsResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final AuthUserRepository authUserRepository;
    private final CommonProperties commonProperties;
    private final UnauthUserRepository unauthUserRepository;

    public UserService(AuthUserRepository authUserRepository, CommonProperties commonProperties, UnauthUserRepository unauthUserRepository) {
        this.authUserRepository = authUserRepository;
        this.commonProperties = commonProperties;
        this.unauthUserRepository = unauthUserRepository;
    }

    public Optional<AuthUser> findUserByLogin(String login) {
        return authUserRepository.findAuthUsersByPhoneNumber(login);
    }

    public PageUserResponse getUsers(Optional<AuthUser.UserType> userType, Integer page) {
        Pageable pageable = PageRequest.of(page, commonProperties.getPageSize());

        return userType.map(type ->
                PageUserResponse.from(authUserRepository.findAuthUsersByUserType(type, pageable)))
                .orElseGet(() -> PageUserResponse.from(authUserRepository.findAllBy(pageable)));
    }

    public UserStatisticsResponse getUserStatistics(int userId) {
        return authUserRepository.getAuthUserStatistics(userId);
    }

    public Optional<UserResponse> findUserByPhone(String phoneNumber) {
        return authUserRepository.findUserByPhoneNumber(phoneNumber);
    }

    public AuthUser addAuthUser(AuthUser newMaster) {
        return authUserRepository.save(newMaster);
    }

    @Transactional
    public void setWorkStatus(int userId, AuthUser.WorkStatus newWorkStatus) {
        authUserRepository.updateWorkStatusById(newWorkStatus, userId);
    }

    @Transactional
    public void setUserType(int userId, AuthUser.UserType newUserType) {
        authUserRepository.updateUserTypeById(newUserType, userId);
        if(newUserType.equals(AuthUser.UserType.CLIENT))
            authUserRepository.updateWorkStatusById(null, userId);
    }
    
    public UnauthUser addUnauthUser(UnauthUser newUser) {
        return unauthUserRepository.save(newUser);
    }
}
