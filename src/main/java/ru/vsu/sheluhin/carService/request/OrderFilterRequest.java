package ru.vsu.sheluhin.carService.request;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Optional;

public record OrderFilterRequest(
        Optional<String> stateNumber,
        Optional<LocalDate> start,
        Optional<LocalDate> end
) {
}
