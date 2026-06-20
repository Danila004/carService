package ru.vsu.sheluhin.carService.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ru.vsu.sheluhin.carService.entity.User;

public record RegistrationRequest(
        @NotBlank(message = "Обязательное поле")
        String userName,
        @NotBlank(message = "Обязательное поле")
        @Pattern(regexp = "^\\w{8}$")
        String password,
        @NotBlank(message = "Обязательное поле")
        @Pattern(regexp = "^8\\d{10}$")
        String phoneNumber) {
}
