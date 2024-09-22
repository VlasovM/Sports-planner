package ru.javlasov.planner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private Long id;

    @Size(min = 2, message = "Имя должен содержать минимум 2 символа")
    private String name;

    @Size(min = 2, message = "Фамилия долна содержать минимум 2 символа")
    private String surname;

    @Pattern(regexp = "[а-яА-Я]*", message = "Отчество не должно содержать цифры")
    private String middleName;

    @NotNull
    @Min(value = 16, message = "Вам должно быть больше 16 лет")
    @Max(value = 150, message = "Вам должно быть меньше 150 лет")
    private Integer age;

    @NotNull(message = "Укажите дату рождения")
    private LocalDate birthday;

    private String biography;

    @Email(message = "Не валидная почта. Пример: test@mail.ru")
    private String email;

    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    private String password;

    @NotNull
    private Long sport;

}
