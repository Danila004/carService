package ru.vsu.sheluhin.carService.response;

import java.time.LocalTime;

public record DateSlotResponse(
        Integer slotId,
        LocalTime visitTime,
        Integer masterId
) {
}
