package ru.javlasov.clinic.api.request;

import lombok.Getter;
import lombok.Setter;
import ru.javlasov.clinic.dto.PatientDto;

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
