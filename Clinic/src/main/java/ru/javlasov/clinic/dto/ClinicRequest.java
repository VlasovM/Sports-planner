package ru.javlasov.clinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClinicRequest {

    private String doctorFullName;

    private String doctorSpecialization;

    private String result;

    private LocalDate visitDate;

    private PatientDto patient;

}
