package ru.vsu.sheluhin.carService.response;

import ru.vsu.sheluhin.carService.entity.User;

public record UserResponse(
        Integer userId,
        String userName,
        String phoneNumber,
        User.UserType userType,
        User.WorkStatus workStatus
) {
}
