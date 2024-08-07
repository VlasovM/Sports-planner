package ru.javlasov.planner.request;

import lombok.Getter;
import lombok.Setter;
import ru.javlasov.planner.dto.PatientDto;

import java.time.LocalDate;

@Getter
@Setter
public class IncomeClinicRequest {

    private String doctorFullName;

    private String doctorSpecialization;

    private String result;

    private LocalDate visitDate;

    private PatientDto patient;

}
