package ru.vsu.sheluhin.carService.request;

import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

public record OrderFilterRequest(
        @RequestParam
        Optional<String> stateNumber,
        @RequestParam
        Optional<LocalDate> start,
        @RequestParam
        Optional<LocalDate> end
) {
}
