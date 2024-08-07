package ru.javlasov.planner.dto;

import lombok.Getter;
import lombok.Setter;
import ru.javlasov.planner.enums.Status;

import java.time.LocalDate;

@Getter
@Setter
public class ClinicRequestDto {

    private Long id;

    private String requestId;

    private Status status;

    private String note;

    private String doctorFullName;

    private String doctorSpecialization;

    private LocalDate dateVisited;

    private PatientDto patient;

    private String result;

}
