package ru.javlasov.planner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javlasov.planner.enums.ArticleStatusDto;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Long id;

    private ArticleStatusDto status;

    @NotBlank(message = "Название не может быть пустым")
    @Size(min = 1, max = 255, message = "Заголовок должен содержать минимум 1 символ и максимум 255 символов")
    private String title;

    @NotBlank(message = "Текст статьи не может быть пустым")
    private String text;

    private LocalDate created;

    private UserDto userDto;

}
