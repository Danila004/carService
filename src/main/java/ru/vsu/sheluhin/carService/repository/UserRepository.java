package ru.vsu.sheluhin.carService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vsu.sheluhin.carService.entity.User;
import ru.vsu.sheluhin.carService.response.UserResponse;
import ru.vsu.sheluhin.carService.response.UserStatisticsResponse;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM Users u WHERE u.phoneNumber=:phoneNumber")
    Optional<User> findAuthUsersByPhoneNumber(String phoneNumber);

    @Query("SELECT u.userId FROM Users u WHERE u.userType=:userType AND u.workStatus='WORK'")
    List<Integer> findAuthUserIdsByUserTypeContaining(User.UserType userType, Sort sort);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Users u SET u.workStatus=:newWorkStatus WHERE u.userId=:userId")
    void updateWorkStatusById(User.WorkStatus newWorkStatus, int userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Users u SET u.userType=:newUserType WHERE u.userId=:userId")
    void updateUserTypeById(User.UserType newUserType, int userId);

    @Query("SELECT pg_advisory_xact_lock(hashtext(:login))")
    void lockOnLogin(String login);

    @Query(value = """
        SELECT 
            MAX(visit_date) AS lastVisitDate,
            COUNT(order_id) AS countOrders,
            COALESCE(SUM(price), 0) AS price,
            COALESCE(AVG(price), 0) AS avgPrice
        FROM orders
        WHERE user_id = :userId
        """, nativeQuery = true)
    UserStatisticsResponse getAuthUserStatistics(int userId);

    @Query("SELECT u.userId, u.userName, u.phoneNumber, u.userType, u.workStatus FROM Users u")
    List<UserResponse> findAllUsers();

    Optional<UserResponse> findUserByPhoneNumber(String phoneNumber);

    Page<UserResponse> findAuthUsersByUserType(User.UserType userType, Pageable pageable);

    Page<UserResponse> findAllBy(Pageable pageable);
}
