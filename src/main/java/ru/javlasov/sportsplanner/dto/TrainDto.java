package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TrainDto {

    private Long id;

    @NotNull(message = "Set date of birthday")
    private LocalDateTime date;

    @NotEmpty(message = "The title of training cannot be empty")
    private String title;

    @Size(max = 2048, message = "The reflection of training is too long")
    private String reflection;

    private Long user;

}
