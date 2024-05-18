package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TrainDto {

    private Long id;

    @NotNull(message = "Укажите дату тренировки!")
    private LocalDateTime date;

    @NotEmpty(message = "Название тренировки не может быть пустой.")
    private String title;

    @Max(value = 2048, message = "Слишком длинное описание тренировки!")
    private String reflection;

    private Long user;

}
