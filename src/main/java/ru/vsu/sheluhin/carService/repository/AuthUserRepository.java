package ru.vsu.sheluhin.carService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.sheluhin.carService.entity.AuthUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {

    @Query("SELECT au FROM AuthUsers au WHERE au.phoneNumber=:phoneNumber")
    Optional<AuthUser> findAuthUsersByPhoneNumber(String phoneNumber);

    @Query("SELECT au FROM AuthUsers au WHERE au.userType=:userType")
    Page<AuthUser> findByUserTypeContaining(AuthUser.UserType userType, Pageable pageable);

    @Query("SELECT au.authUserId FROM AuthUsers au WHERE au.userType=:userType AND au.workStatus='WORK'")
    List<Integer> findAuthUserIdsByUserTypeContaining(AuthUser.UserType userType, Sort sort);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE AuthUsers au SET au.workStatus=:newWorkStatus WHERE au.authUserId=:masterId")
    void updateWorkStatusById(AuthUser.WorkStatus newWorkStatus, int masterId);

    @Query("SELECT pg_advisory_xact_lock(hashtext(:login))")
    void lockOnLogin(String login);
}
