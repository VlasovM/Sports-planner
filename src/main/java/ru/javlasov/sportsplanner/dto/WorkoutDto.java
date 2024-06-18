package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDto {

    private Long id;

    @NotBlank(message = "Set date of train")
    private String date;

    @NotEmpty(message = "The title of training cannot be empty")
    private String title;

    @Size(max = 2048, message = "The reflection of training is too long")
    private String reflection;

    private UserDto userDto;

}
