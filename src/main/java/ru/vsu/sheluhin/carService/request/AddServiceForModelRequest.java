package ru.vsu.sheluhin.carService.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class AddServiceForModelRequest {
    @NotBlank
    int serviceId;

    @Column(precision = 9, scale = 2)
    @Digits(integer = 7, fraction = 2)
    BigDecimal price;
}
