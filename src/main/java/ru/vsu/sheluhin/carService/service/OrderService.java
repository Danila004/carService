package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.AuthUser;
import ru.vsu.sheluhin.carService.entity.Order;
import ru.vsu.sheluhin.carService.repository.OrderRepository;
import ru.vsu.sheluhin.carService.request.OrderFilterRequest;
import ru.vsu.sheluhin.carService.response.OrderDetailsForAdminResponse;
import ru.vsu.sheluhin.carService.response.OrderDetailsForUserOrMasterResponse;
import ru.vsu.sheluhin.carService.response.PageOrderResponse;
import ru.vsu.sheluhin.carService.response.UserAndMaster;
import ru.vsu.sheluhin.carService.specification.OrderSpecifications;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CommonProperties commonProperties;

    public OrderService(OrderRepository orderRepository, CommonProperties commonProperties) {
        this.orderRepository = orderRepository;
        this.commonProperties = commonProperties;
    }

    public PageOrderResponse getOrdersByUserId(int userId, OrderFilterRequest filter, int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getOrderSortBy()).descending());

        Specification<Order> spec = Specification
                .where(OrderSpecifications.hasUserId(userId))
                .and(OrderSpecifications.hasStateNumber(filter.stateNumber()))
                .and(OrderSpecifications.hasDateGreaterThanOrEqualTo(filter.start()))
                .and(OrderSpecifications.hasDateLessThanOrEqualTo(filter.end()));

        return PageOrderResponse.from(orderRepository.findAll(spec, pageable));
    }

    public void delete(int orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order newOrder(Order newOrder) {
        return orderRepository.save(newOrder);
    }

    @Transactional
    public void setStatus(int orderId, Order.OrderStatus orderStatus) {
        orderRepository.updateOrderStatusById(orderStatus, orderId);
    }

    public OrderDetailsForUserOrMasterResponse getOrderDetailsForUserOrMaster(int orderId) {
        return new OrderDetailsForUserOrMasterResponse(orderRepository.getServicesForOrder(orderId));
    }

    public OrderDetailsForAdminResponse getOrderDetailsForAdmin(int orderId) {
        OrderDetailsForUserOrMasterResponse serviceDetails =
                new OrderDetailsForUserOrMasterResponse(orderRepository.getServicesForOrder(orderId));
        UserAndMaster userAndMasterDetails = orderRepository.getUserAndMasterForOrder(orderId);
        return new OrderDetailsForAdminResponse(serviceDetails.services(),
                userAndMasterDetails.userName(),
                userAndMasterDetails.userPhoneNumber(),
                userAndMasterDetails.masterName(),
                userAndMasterDetails.masterPhoneNumber());
    }

    public Page<Order> getOrdersByMasterId(int masterId, int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getOrderSortBy()).descending());

        return orderRepository.findAllByMasterId(masterId, pageable);
    }

    public PageOrderResponse getOrders(OrderFilterRequest filter, int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getOrderSortBy()).descending());

        Specification<Order> spec = Specification
                .where(OrderSpecifications.hasStateNumber(filter.stateNumber()))
                .and(OrderSpecifications.hasDateGreaterThanOrEqualTo(filter.start()))
                .and(OrderSpecifications.hasDateLessThanOrEqualTo(filter.end()));

        return PageOrderResponse.from(orderRepository.findAll(spec, pageable));
    }

}
