package ru.vsu.sheluhin.carService.request;

import ru.vsu.sheluhin.carService.entity.Order;
import ru.vsu.sheluhin.carService.entity.Service;

import java.util.List;

public record CreateOrderRequest(Order newOrder, List<Service> serviceList) {
}
