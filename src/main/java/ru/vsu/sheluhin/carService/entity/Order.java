package ru.vsu.sheluhin.carService.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @NotNull
    private LocalDate visitDate;

    @NotNull
    private LocalTime visitTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotNull
    private String userName;

    @NotNull
    private String userPhoneNumber;

    @Null
    private Integer userId;

    @NotNull
    private Integer masterId;

    @NotNull
    private String brandName;

    @NotNull
    private String modelName;

    @NotNull
    private String stateNumber;

    @NotNull
    @Column(precision = 9, scale = 2)
    @Digits(integer = 7, fraction = 2)
    private BigDecimal price;

    public enum OrderStatus{
        REGISTRED, WORK, READY
    }
}
