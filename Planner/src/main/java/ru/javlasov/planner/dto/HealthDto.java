package ru.javlasov.planner.dto;

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

    private LocalDate date;

    private String clinic;

    private String doctorSpecialization;

    private String doctorFullName;

    private String result;

    private UserDto userDto;

}
