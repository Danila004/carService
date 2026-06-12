package ru.vsu.sheluhin.carService.response;

import ru.vsu.sheluhin.carService.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record OrderResponse (
        Integer orderId,
        String brandName,
        String modelName,
        String stateNumber,
        LocalDate visitDate,
        LocalTime visitTime,
        BigDecimal price,
        Order.OrderStatus orderStatus
)
{}
