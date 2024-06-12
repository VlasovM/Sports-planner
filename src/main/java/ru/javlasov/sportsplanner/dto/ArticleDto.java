package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javlasov.sportsplanner.enums.ArticleStatusDto;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Long id;

    private ArticleStatusDto status;

    @NotBlank(message = "The title cannot be empty")
    @Size(min = 1, max = 255, message = "The title must be between 1 and 255 characters")
    private String title;

    @NotBlank(message = "The text of article cannot be empty")
    private String text;

    private LocalDate created;

    private UserDto userDto;

}
