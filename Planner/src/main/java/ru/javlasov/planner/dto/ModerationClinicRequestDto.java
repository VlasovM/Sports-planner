package ru.javlasov.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ModerationClinicRequestDto {

    private String requestId;

    private LocalDate dateVisited;

    private String doctorFullName;

    private String doctorSpecialization;

    private String clinic;

    private List<UserDto> users;

}
