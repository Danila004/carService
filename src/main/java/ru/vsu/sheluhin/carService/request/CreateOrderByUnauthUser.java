package ru.vsu.sheluhin.carService.request;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.vsu.sheluhin.carService.entity.Order;
import ru.vsu.sheluhin.carService.entity.Service;
import ru.vsu.sheluhin.carService.entity.UnauthUser;

import java.util.List;

public record CreateOrderByUnauthUser (UnauthUser newUser, Order newOrder, List<Service> serviceList) {
}
