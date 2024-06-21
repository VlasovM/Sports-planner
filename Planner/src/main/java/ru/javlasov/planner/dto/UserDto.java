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

    @Size(min = 2, message = "Name must be minimum 2 characters")
    private String name;

    @Size(min = 2, message = "Surname must be minimum 2 characters")
    private String surname;

    @Pattern(regexp = "[а-яА-Я]*", message = "Middle name cannot contains digits")
    private String middleName;

    @NotNull
    @Min(value = 16, message = "You must be over 16 years old")
    @Max(value = 150, message = "You can't be more than 150 years old.")
    private Integer age;

    @NotNull(message = "The birthday cannot be empty")
    private LocalDate birthday;

    private String biography;

    @Email(message = "The email is not valid. Example: test@mail.ru")
    private String email;

    @Size(min = 8, message = "The password must be minimum 8 characters")
    private String password;

    @NotNull
    private Long sport;

}
