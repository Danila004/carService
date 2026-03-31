package ru.vsu.sheluhin.carService.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.AuthUser;
import ru.vsu.sheluhin.carService.entity.Order;
import ru.vsu.sheluhin.carService.service.OrderService;
import ru.vsu.sheluhin.carService.service.UserService;

@RestController
@RequestMapping(path = "/orders",
                produces = "application/json"
)
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Page<Order> getOrders(@RequestParam(defaultValue = "0") int page) {
        return orderService.getOrders(page);
    }

    @GetMapping(path = "/{masterId}")
    public Page<Order> getOrders(@PathVariable int masterId, @RequestParam(defaultValue = "0") int page) {
        return orderService.getOrdersByMasterId(masterId, page);
    }

    @PatchMapping(path = "/{orderId}/setStatus")
    public ResponseEntity<Void> setStatus(@PathVariable int orderId, @RequestParam Order.OrderStatus orderStatus) {
        orderService.setStatus(orderId, orderStatus);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{orderId}")
    public ResponseEntity<Void> delete(@PathVariable int orderId) {
        orderService.delete(orderId);
        return ResponseEntity.ok().build();
    }


}
