package ru.javlasov.sportsplanner.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javlasov.sportsplanner.enums.ArticleStatusEnum;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Long id;

    private ArticleStatusEnum status;

    @NotNull(message = "The title cannot be empty")
    @Size(min = 1, max = 255, message = "The title must be between 1 and 255 characters")
    private String title;

    @NotNull(message = "The text of article cannot be empty")
    private String text;

    private LocalDate created;

    private Long user;

    private String userFullName;

}
