package ru.javlasov.clinic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HealthInformationDto {

    @NotBlank(message = "Укажите имя пациента")
    private String patientName;

    private String patientMiddleName;

    @NotBlank(message = "Укажите фамилию пациента")
    private String patientSurname;

    @NotBlank(message = "Укажите дату рождения пациента")
    private LocalDate patientBirthday;

    @NotBlank(message = "Укажите дату визита пациента")
    private LocalDate visited;

    @NotBlank(message = "Укажите результат посещения")
    private String result;

}
