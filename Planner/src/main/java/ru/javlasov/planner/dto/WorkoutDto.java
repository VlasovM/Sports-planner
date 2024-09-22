package ru.javlasov.planner.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDto {

    private Long id;

    @NotNull(message = "Укажите дату тренировки")
    private LocalDateTime date;

    @NotEmpty(message = "Название тренировки не может быть пустым")
    private String title;

    @Size(max = 2048, message = "Слишком длинная рефлексия")
    private String reflection;

    private UserDto userDto;

}
