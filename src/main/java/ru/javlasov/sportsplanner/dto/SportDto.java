package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SportDto {

    private Long id;

    @NotEmpty
    private String title;

}
