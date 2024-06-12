package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TournamentDto {

    private Long id;

    @NotNull(message = "Please set the tournament day")
    private LocalDate date;

    @NotEmpty(message = "Please set the tournament title")
    private String title;

    @NotEmpty(message = "Please set the your opponent")
    private String opponent;

    @NotEmpty(message = "Please set the result of tournament")
    private String result;

    @Size(max = 2048, message = "The reflection of tournament is too long")
    private String reflection;

    private UserDto user;

}
