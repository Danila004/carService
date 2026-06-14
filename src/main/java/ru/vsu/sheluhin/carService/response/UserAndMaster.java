package ru.vsu.sheluhin.carService.response;

public record UserAndMaster(
        String userName,
        String userPhoneNumber,
        String masterName,
        String masterPhoneNumber
) {
}
