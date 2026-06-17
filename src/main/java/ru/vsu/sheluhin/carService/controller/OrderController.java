package ru.vsu.sheluhin.carService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.Order;
import ru.vsu.sheluhin.carService.request.OrderFilterRequest;
import ru.vsu.sheluhin.carService.response.OrderDetailsForAdminResponse;
import ru.vsu.sheluhin.carService.response.OrderDetailsForUserOrMasterResponse;
import ru.vsu.sheluhin.carService.response.PageOrderResponse;
import ru.vsu.sheluhin.carService.service.OrderService;

@RestController
@RequestMapping(path = "/orders",
                produces = "application/json"
)
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public PageOrderResponse getOrders(OrderFilterRequest filter, @RequestParam int page) {
        return orderService.getOrders(filter, page);
    }

    @DeleteMapping(path = "/{orderId}")
    public ResponseEntity<Void> delete(@PathVariable int orderId) {
        orderService.delete(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{orderId}/simpleDetails")
    public OrderDetailsForUserOrMasterResponse getOrderDetailsForUserOrMaster(@PathVariable int orderId) {
        return orderService.getOrderDetailsForUserOrMaster(orderId);
    }

    @GetMapping(path = "/{orderId}/fullDetails")
    public OrderDetailsForAdminResponse getOrderDetailsForAdmin(@PathVariable int orderId) {
        return orderService.getOrderDetailsForAdmin(orderId);
    }

    @PatchMapping(path = "/{orderId}")
    public ResponseEntity<Void> setUserType(@PathVariable int orderId, @RequestBody Order.OrderStatus newOrderStatus) {
        orderService.setOrderStatus(orderId, newOrderStatus);
        return ResponseEntity.ok().build();
    }
}
