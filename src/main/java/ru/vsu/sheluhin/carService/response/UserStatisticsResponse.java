package ru.vsu.sheluhin.carService.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserStatisticsResponse(
        LocalDate lastVisitDate,
        Long countOrders,
        BigDecimal price,
        BigDecimal avgPrice
) {
}
