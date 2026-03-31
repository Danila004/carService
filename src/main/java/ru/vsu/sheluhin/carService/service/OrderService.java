package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.AuthUser;
import ru.vsu.sheluhin.carService.entity.Order;
import ru.vsu.sheluhin.carService.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CommonProperties commonProperties;

    public OrderService(OrderRepository orderRepository, CommonProperties commonProperties) {
        this.orderRepository = orderRepository;
        this.commonProperties = commonProperties;
    }

    public Page<Order> getOrdersByUserId(int userId, int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getOrderSortBy()).descending());

        return orderRepository.findAllByAuthUserId(userId, pageable);
    }

    public void delete(int orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order newOrder(Order newOrder) {
        return orderRepository.save(newOrder);
    }

    public void setStatus(int orderId, Order.OrderStatus orderStatus) {
        orderRepository.updateOrderStatusById(
                Order.OrderStatus.values()[orderStatus.ordinal() + 1].toString(),
                orderId);
    }

    public Page<Order> getOrdersByMasterId(int masterId, int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getOrderSortBy()).descending());

        return orderRepository.findAllByMasterId(masterId, pageable);
    }

    public Page<Order> getOrders(int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getOrderSortBy()).descending());

        return orderRepository.findAll(pageable);
    }

}
