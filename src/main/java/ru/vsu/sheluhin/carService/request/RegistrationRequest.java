package ru.vsu.sheluhin.carService.request;

import ru.vsu.sheluhin.carService.entity.AuthUser;

public record RegistrationRequest(String username,
                                  String surname,
                                  String password,
                                  String PhoneNumber,
                                  AuthUser.UserType userType) {
}
