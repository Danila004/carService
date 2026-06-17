package ru.vsu.sheluhin.carService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.*;
import ru.vsu.sheluhin.carService.request.OrderFilterRequest;
import ru.vsu.sheluhin.carService.response.*;
import ru.vsu.sheluhin.carService.service.*;

import java.time.LocalDate;
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

    public UserController(UserService userService, OrderService orderService, DateSlotService dateSlotService) {
        this.userService = userService;
        this.orderService = orderService;
        this.dateSlotService = dateSlotService;
    }

//    @GetMapping(path = "/{login}")
//    public ProfileResponse profile(@PathVariable String login) {
//        Optional<User> user = userService.findUserByLogin(SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getName());
//
//        Page<Order> orders = orderService.getOrdersByUserId(user.get().getAuthUserId(), 0);
//        return new ProfileResponse(user.get(), orders);
//    }

    @GetMapping
    public PageUserResponse getUsers(@RequestParam Optional<User.UserType> userType, @RequestParam Integer page) {
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
    public User addUser(@RequestBody User newUser) {
        return userService.addUser(newUser);
    }

    @PatchMapping(path = "/{userId}/setWorkStatus")
    public ResponseEntity<Void> setWorkStatus(@PathVariable int userId, @RequestBody User.WorkStatus newWorkStatus) {
        userService.setWorkStatus(userId, newWorkStatus);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{userId}/setUserType")
    public ResponseEntity<Void> setUserType(@PathVariable int userId, @RequestBody User.UserType newUserType) {
        userService.setUserType(userId, newUserType);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{userId}/orders")
    public PageOrderResponse getOrders(@PathVariable int userId, OrderFilterRequest filter, @RequestParam int page) {
        return orderService.getOrdersByUserId(userId, filter, page);
    }

    @GetMapping(path = "/{userId}/ordersToWork")
    public List<OrderResponse> getOrders(@PathVariable int userId, @RequestParam Optional<LocalDate> date) {
        return orderService.getOrdersByMasterId(userId, date);
    }
}
