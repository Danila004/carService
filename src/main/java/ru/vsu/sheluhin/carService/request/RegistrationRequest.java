package ru.vsu.sheluhin.carService.request;

import ru.vsu.sheluhin.carService.entity.User;

public record RegistrationRequest(String username,
                                  String surname,
                                  String password,
                                  String PhoneNumber,
                                  User.UserType userType) {
}
