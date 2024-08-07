package ru.javlasov.clinic.dto;

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
