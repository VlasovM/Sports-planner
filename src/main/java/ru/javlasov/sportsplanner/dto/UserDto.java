package ru.javlasov.sportsplanner.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {

    private Long id;

    @Size(min = 2, message = "Имя должно содержать минимум 2 символа.")
    private String name;

    @Size(min = 2, message = "Фамилия должна содержать минимум 2 символа.")
    private String surname;

    @Pattern(regexp = "[а-яА-Я]*", message = "Отчество не должно содержать цифры.")
    private String middleName;

    @NotNull
    @Min(value = 16, message = "Вам должно быть больше 16 лет.")
    @Max(value = 150, message = "Вам не может быть больше 150-ти лет.")
    private Integer age;

    @NotEmpty(message = "Поле дата рождения не может быть пустым")
    private LocalDateTime birthday;

    private String biography;

    @Email(message = "Почта не прошла валидацию. Пример: test@mail.ru")
    private String email;

    @Size(min = 8, message = "Пароль должен быть минимум 8 символов")
    private String password;

}
