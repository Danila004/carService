package ru.vsu.sheluhin.carService.response;

import org.springframework.data.domain.Page;
import ru.vsu.sheluhin.carService.entity.User;
import ru.vsu.sheluhin.carService.entity.Order;

public record ProfileResponse(User user, Page<Order> orderPage) {
}
