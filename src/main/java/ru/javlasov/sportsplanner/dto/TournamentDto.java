package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TournamentDto {

    private Long id;

    @NotNull(message = "Укажите дату турнира!")
    private LocalDate date;

    @NotEmpty(message = "Укажите название турнира!")
    private String title;

    @NotEmpty(message = "Вы не указали вашего соперника!")
    private String opponent;

    @NotEmpty(message = "Укажите результат!")
    private String result;

    @Max(value = 2048, message = "Слишком длинное описание турнира")
    private String reflection;

    private Long user;

}
