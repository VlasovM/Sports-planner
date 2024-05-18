package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HealthDto {

    private Long id;

    @NotNull(message = "Заполните дату посещения врача")
    private LocalDate date;

    @NotEmpty(message = "Укажите название клиники, в которой проходили обследование")
    private String clinic;

    @NotEmpty(message = "Укажите специализацию врача")
    private String doctorSpecialization;

    @NotEmpty(message = "Укажите ФИО доктора")
    private String doctorFullName;

    @NotEmpty(message = "Напишите результат посещения врача")
    @Size(max = 2048, message = "Слишком длинное описание результата!")
    private String result;

    private Long user;

}
