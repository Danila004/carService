package ru.vsu.sheluhin.carService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity(name = "ServicesInOrder")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceOrderId;

    @NotNull
    private int serviceId;

    @NotNull
    private int orderId;
}
