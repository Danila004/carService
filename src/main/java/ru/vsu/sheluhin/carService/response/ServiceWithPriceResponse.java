package ru.vsu.sheluhin.carService.response;

import ru.vsu.sheluhin.carService.entity.Status;

import java.math.BigDecimal;

public record ServiceWithPriceResponse(
    Integer priceId,
    Integer serviceId,
    String serviceName,
    Integer modelId,
    BigDecimal price,
    Status status
) { }
