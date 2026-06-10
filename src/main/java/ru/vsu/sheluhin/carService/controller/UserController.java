package ru.vsu.sheluhin.carService.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.*;
import ru.vsu.sheluhin.carService.request.CreateOrderByAuthUser;
import ru.vsu.sheluhin.carService.request.CreateOrderByUnauthUser;
import ru.vsu.sheluhin.carService.response.PageUserResponse;
import ru.vsu.sheluhin.carService.response.ProfileResponse;
import ru.vsu.sheluhin.carService.response.UserResponse;
import ru.vsu.sheluhin.carService.response.UserStatisticsResponse;
import ru.vsu.sheluhin.carService.service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users",
                produces = "application/json"
)
@CrossOrigin(origins = "http://localhost:5173")
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
    public PageUserResponse getUsers(@RequestParam Optional<AuthUser.UserType> userType, @RequestParam Integer page) {
        return userService.getUsers(userType, page);
    }

    @GetMapping(path = "/find")
    public Optional<UserResponse> findUserByPhone(@RequestParam String phoneNumber) {
        return userService.findUserByPhone(phoneNumber);
    }

    @GetMapping(path = "/{userId}/statistics")
    public UserStatisticsResponse getUserStatistics(@PathVariable int userId) {
        return userService.getUserStatistics(userId);
    }

    @PostMapping
    public AuthUser addAuthUser(@RequestBody AuthUser newAuthUser) {
        AuthUser newAuthUserDb = userService.addAuthUser(newAuthUser);
        if (newAuthUser.getUserType().equals(AuthUser.UserType.MASTER))
            dateSlotService.createSlotsForNewMaster(newAuthUserDb.getAuthUserId());
        return newAuthUserDb;
    }

    @PatchMapping(path = "/{userId}/setWorkStatus")
    public ResponseEntity<Void> setWorkStatus(@PathVariable int userId, @RequestBody AuthUser.WorkStatus newWorkStatus) {
        userService.setWorkStatus(userId, newWorkStatus);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{userId}/setUserType")
    public ResponseEntity<Void> setUserType(@PathVariable int userId, @RequestBody AuthUser.UserType newUserType) {
        userService.setUserType(userId, newUserType);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/newOrder")
    public Order newOrder(@PathVariable int userId, @RequestBody CreateOrderByAuthUser request) {
        Order newOrder = request.newOrder();
        newOrder.setAuthUserId(userId);
        Order newOrderDb = orderService.newOrder(newOrder);
        serviceInOrderService.addServiceInOrder(newOrderDb.getOrderId(), request.serviceList());
        return newOrderDb;
    }

    @PostMapping("/newOrder")
    public ResponseEntity<Void> newOrder(@RequestBody CreateOrderByUnauthUser request) {
        UnauthUser newUserDb = userService.addUnauthUser(request.newUser());
        Order newOrder = request.newOrder();
        newOrder.setUnauthUserId(newUserDb.getUnauthUserId());
        Order newOrderDb = orderService.newOrder(newOrder);
        serviceInOrderService.addServiceInOrder(newOrderDb.getOrderId(), request.serviceList());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
