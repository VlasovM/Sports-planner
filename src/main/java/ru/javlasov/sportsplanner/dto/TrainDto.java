package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainDto {

    private Long id;

    @NotBlank(message = "Set date of train")
    private String date;

    @NotEmpty(message = "The title of training cannot be empty")
    private String title;

    @Size(max = 2048, message = "The reflection of training is too long")
    private String reflection;

    private Long user;

}
