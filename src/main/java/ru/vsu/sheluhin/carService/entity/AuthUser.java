package ru.vsu.sheluhin.carService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity(name = "AuthUsers")
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authUserId;

    @NotBlank(message = "Обязательное поле")
    private String userName;

    @NotBlank(message = "Обязательное поле")
    private String surname;

    @NotBlank(message = "Обязательное поле")
    @Pattern(regexp = "^8\\d{10}$")
    @Column(unique = true)
    private String phoneNumber;

    @NotBlank(message = "Обязательное поле")
    @Pattern(regexp = "^\\w{8}$")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;

    public enum UserType {
        ADMIN, MASTER, CLIENT
    }

    public enum WorkStatus {
        WORK, NOT_WORK, SICK
    }
}
