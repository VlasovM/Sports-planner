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

    @NotNull(message = "Set date of train")
    private LocalDateTime date;

    @NotEmpty(message = "The title of training cannot be empty")
    private String title;

    @Size(max = 2048, message = "The reflection of training is too long")
    private String reflection;

    private UserDto userDto;

}
