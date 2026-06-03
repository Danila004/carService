package ru.vsu.sheluhin.carService.response;

import ru.vsu.sheluhin.carService.entity.AuthUser;

public record UserResponse(
        Integer authUserId,
        String userName,
        String phoneNumber,
        AuthUser.UserType userType,
        AuthUser.WorkStatus workStatus
) {
}
