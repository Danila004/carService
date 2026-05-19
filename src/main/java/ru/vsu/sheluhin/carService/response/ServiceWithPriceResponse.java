package ru.vsu.sheluhin.carService.response;

import ru.vsu.sheluhin.carService.entity.Status;

import java.math.BigDecimal;

public record ServiceWithPriceResponse(
    Integer serviceId,
    String serviceName,
    BigDecimal price,
    Status status
) { }
