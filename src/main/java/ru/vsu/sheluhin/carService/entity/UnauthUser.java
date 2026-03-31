package ru.vsu.sheluhin.carService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "UnauthUsers")
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"phone_number"})
)
public class UnauthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int unauthUserId;

    @NotBlank(message = "Обязательное поле")
    private String userName;

    @NotBlank(message = "Обязательное поле")
    private String surname;

    @NotBlank(message = "Обязательное поле")
    @Pattern(regexp = "^8\\d{10}$")
    private String phoneNumber;
}
