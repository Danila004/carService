package ru.vsu.sheluhin.carService.response;

import ru.vsu.sheluhin.carService.entity.User;

public record UserResponse(
        Integer authUserId,
        String userName,
        String phoneNumber,
        User.UserType userType,
        User.WorkStatus workStatus
) {
}
