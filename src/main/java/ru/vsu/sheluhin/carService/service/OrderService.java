package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.Order;
import ru.vsu.sheluhin.carService.entity.ServiceInOrder;
import ru.vsu.sheluhin.carService.repository.OrderRepository;
import ru.vsu.sheluhin.carService.repository.ServiceInOrderRepository;
import ru.vsu.sheluhin.carService.request.CreateOrderRequest;
import ru.vsu.sheluhin.carService.request.OrderFilterRequest;
import ru.vsu.sheluhin.carService.response.*;
import ru.vsu.sheluhin.carService.specification.OrderSpecifications;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ServiceInOrderRepository serviceInOrderRepository;
    private final CommonProperties commonProperties;

    public OrderService(OrderRepository orderRepository, ServiceInOrderRepository serviceInOrderRepository, CommonProperties commonProperties) {
        this.orderRepository = orderRepository;
        this.serviceInOrderRepository = serviceInOrderRepository;
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

    public List<OrderResponse> getOrdersByMasterId(int masterId, Optional<LocalDate> date) {
        return date.map(value -> orderRepository.findAllByMasterIdIsAndVisitDate(masterId, value))
                .orElse(orderRepository.findAllByMasterIdIsAndVisitDate(masterId, LocalDate.now()));
    }

    public void delete(int orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public void add(CreateOrderRequest request) {
        Order newOrderDb = orderRepository.save(request.newOrder());
        for (Integer serviceId : request.services()) {
            serviceInOrderRepository.save(new ServiceInOrder(0, serviceId, newOrderDb.getOrderId()));
        }
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

    @Transactional
    public void setOrderStatus(int orderId, Order.OrderStatus newOrderStatus) {
        orderRepository.updateOrderStatusById(newOrderStatus, orderId);
    }
}
