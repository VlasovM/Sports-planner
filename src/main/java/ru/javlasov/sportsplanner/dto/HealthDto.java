package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthDto {

    private Long id;

    @NotNull(message = "Please set the date of visit a doctor")
    private String date;

    @NotEmpty(message = "Please set the clinic name")
    private String clinic;

    @NotEmpty(message = "Please set the doctor specialization")
    private String doctorSpecialization;

    @NotEmpty(message = "Please set the doctor full name")
    private String doctorFullName;

    @NotEmpty(message = "Please set the result of visiting")
    @Size(max = 2048, message = "The result of visiting is too long")
    private String result;

    private UserDto user;

}
