package ru.vsu.sheluhin.carService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity(name = "Models")
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int modelId;

    @NotBlank
    private String modelName;

    @NotNull
    private int brandId;

    @NotNull
    private Integer modelYear;

    @Enumerated(EnumType.STRING)
    private Status status;
}
