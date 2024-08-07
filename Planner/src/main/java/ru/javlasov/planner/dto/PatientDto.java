package ru.javlasov.planner.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDto {

    private String name;

    private String middleName;

    private String surname;

    private LocalDate birthday;

}
