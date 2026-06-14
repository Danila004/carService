package ru.vsu.sheluhin.carService.response;

import java.util.List;

public record OrderDetailsForAdminResponse(
        List<String> services,
        String userName,
        String userPhoneNumber,
        String masterName,
        String masterPhoneNumber
) {
}
