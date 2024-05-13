package ru.javlasov.sportsplanner.dto;

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
public class ArticleDto {

    private Long id;

    private ArticleStatusDto status;

    @NotNull(message = "Заголовок статьи не может быть пустым.")
    @Size(min = 1, max = 255, message = "Заголовок должен быть от 1 до 255 символов.")
    private String title;

    @NotNull(message = "Текст статьи не может быть пустым")
    private String text;

    private LocalDate created;

    private Long user;

}
