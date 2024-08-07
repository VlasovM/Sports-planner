package ru.javlasov.planner.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClinicResponseStatus {

    SUCCEED(100L, "Успешно принят системой \"Спортивный планировщик\"", Status.SUCCEED),
    NOT_VALID_DATA(101L, "Невалидные данные. Проверьте, заполнены поля Имя, Фамилия или дата рождения пациента.",
            Status.ERROR),
    USER_NOT_FOUND(102L, "Не удалось найти пользователя в системе, перепроверьте данные", Status.ERROR);


    private final Long id;

    private final String title;

    private final Status status;

}
