package ru.vsu.sheluhin.carService.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.vsu.sheluhin.carService.entity.Order;
import ru.vsu.sheluhin.carService.response.OrderResponse;
import ru.vsu.sheluhin.carService.response.UserAndMaster;

import java.time.LocalDate;
import java.util.List;

@NullMarked
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    List<OrderResponse> findAllByMasterIdIsAndVisitDate(int masterId, LocalDate visitDate);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Orders o SET o.orderStatus=:newOrderStatus WHERE o.orderId=:orderId")
    void updateOrderStatusById(Order.OrderStatus newOrderStatus, int orderId);

    @Query(value = """
            SELECT s.service_name
            FROM services_in_order so
            JOIN services s ON s.service_Id=so.service_id
            WHERE so.order_id=:orderId
            """, nativeQuery = true)
    List<String> getServicesForOrder(int orderId);

    @Query(value = """
            SELECT o.user_name AS userName, 
            o.user_phone_number AS userPhoneNumber, 
            au.user_name AS masterName, 
            au.phone_number AS masterPhoneNumber
            FROM orders o 
            JOIN auth_users au ON au.auth_user_id=o.master_id
            WHERE o.order_id=:orderId
            """, nativeQuery = true)
    UserAndMaster getUserAndMasterForOrder(int orderId);
}
