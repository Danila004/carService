package ru.vsu.sheluhin.carService.response;

import org.springframework.data.domain.Page;
import ru.vsu.sheluhin.carService.entity.AuthUser;
import ru.vsu.sheluhin.carService.entity.Order;

public record ProfileResponse(AuthUser user, Page<Order> orderPage) {
}
