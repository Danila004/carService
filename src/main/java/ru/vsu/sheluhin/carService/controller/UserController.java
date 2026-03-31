package ru.vsu.sheluhin.carService.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.*;
import ru.vsu.sheluhin.carService.response.ProfileResponse;
import ru.vsu.sheluhin.carService.service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users",
                produces = "application/json"
)
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final DateSlotService dateSlotService;
    private final ServiceInOrderService serviceInOrderService;

    public UserController(UserService userService, OrderService orderService, DateSlotService dateSlotService, ServiceInOrderService serviceInOrderService) {
        this.userService = userService;
        this.orderService = orderService;
        this.dateSlotService = dateSlotService;
        this.serviceInOrderService = serviceInOrderService;
    }

    @GetMapping(path = "/{login}")
    public ProfileResponse profile(@PathVariable String login) {
        Optional<AuthUser> user = userService.findUserByLogin(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());

        Page<Order> orders = orderService.getOrdersByUserId(user.get().getAuthUserId(), 0);
        return new ProfileResponse(user.get(), orders);
    }

    @GetMapping
    public Page<AuthUser> getUsers(@RequestParam AuthUser.UserType userType, @RequestParam(defaultValue = "0") int page) {
        return userService.getUsers(userType, page);
    }

    @PostMapping
    public AuthUser addAuthUser(@RequestBody AuthUser newAuthUser) {
        AuthUser newAuthUserDb = userService.addAuthUser(newAuthUser);
        if (newAuthUser.getUserType().equals(AuthUser.UserType.MASTER))
            dateSlotService.createSlotsForNewMaster(newAuthUserDb.getAuthUserId());
        return newAuthUserDb;
    }

    @PatchMapping(path = "/{masterId}/setWorkStatus")
    public ResponseEntity<Void> setStatus(@PathVariable int masterId, @RequestBody AuthUser.WorkStatus newWorkStatus) {
        userService.setWorkStatus(masterId, newWorkStatus);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/newOrder")
    public Order newOrder(@PathVariable int userId, @RequestBody Order newOrder, @RequestBody List<Service> serviceList) {
        newOrder.setAuthUserId(userId);
        Order newOrderDb = orderService.newOrder(newOrder);
        serviceInOrderService.addServiceInOrder(newOrderDb.getOrderId(), serviceList);
        return newOrderDb;
    }

    @PostMapping("/newOrder")
    public ResponseEntity<Void> newOrder(@RequestBody UnauthUser newUser, @RequestBody Order newOrder, @RequestBody List<Service> serviceList) {
        UnauthUser newUserDb = userService.addUnauthUser(newUser);
        newOrder.setUnauthUserId(newUserDb.getUnauthUserId());
        Order newOrderDb = orderService.newOrder(newOrder);
        serviceInOrderService.addServiceInOrder(newOrderDb.getOrderId(), serviceList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
