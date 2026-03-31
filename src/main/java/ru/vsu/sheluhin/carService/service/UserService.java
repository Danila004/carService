package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.AuthUser;
import ru.vsu.sheluhin.carService.entity.UnauthUser;
import ru.vsu.sheluhin.carService.repository.AuthUserRepository;
import ru.vsu.sheluhin.carService.repository.UnauthUserRepository;

import java.util.Optional;

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

    public Page<AuthUser> getUsers(AuthUser.UserType userType, int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getEmployerSortBy()).descending());

        return authUserRepository.findByUserTypeContaining(userType, pageable);
    }

    public AuthUser addAuthUser(AuthUser newMaster) {
        return authUserRepository.save(newMaster);
    }

    @Transactional
    public void setWorkStatus(int masterId, AuthUser.WorkStatus newWorkStatus) {
        authUserRepository.updateWorkStatusById(newWorkStatus, masterId);
    }
    
    public UnauthUser addUnauthUser(UnauthUser newUser) {
        return unauthUserRepository.save(newUser);
    }
}
