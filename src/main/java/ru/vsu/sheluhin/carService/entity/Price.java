package ru.vsu.sheluhin.carService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.Duration;

@Data
@Entity(name = "Prices")
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceId;

    @NotNull
    private int serviceId;

    @NotNull
    private int modelId;

    @Column(precision = 9, scale = 2)
    @Digits(integer = 7, fraction = 2)
    private BigDecimal price;
}
