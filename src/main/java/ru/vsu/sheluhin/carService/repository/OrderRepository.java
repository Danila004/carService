package ru.vsu.sheluhin.carService.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.sheluhin.carService.entity.AuthUser;
import ru.vsu.sheluhin.carService.entity.Order;
import ru.vsu.sheluhin.carService.entity.UnauthUser;

@NullMarked
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findAllByAuthUserId(int authUserId, Pageable pageable);

    Page<Order> findAllByMasterId(int masterId, Pageable pageable);

    Page<Order> findAll(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Orders o SET o.orderStatus=:orderStatus WHERE o.orderId=:orderId")
    void updateOrderStatusById(@Param("orderStatus") String orderStatus, @Param("orderId") int orderId);
}
