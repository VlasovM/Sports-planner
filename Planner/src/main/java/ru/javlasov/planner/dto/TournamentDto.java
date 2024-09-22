package ru.javlasov.planner.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDto {

    private Long id;

    @NotNull(message = "Укажите дату турнира")
    private LocalDate date;

    @NotEmpty(message = "Укажите название турнира")
    private String title;

    @NotEmpty(message = "Укажите вашего соперника")
    private String opponent;

    @NotEmpty(message = "Укажите результат турнира")
    private String result;

    @Size(max = 2048, message = "Рефлексия по турниру слишком длинная")
    private String reflection;

    private UserDto userDto;

}
