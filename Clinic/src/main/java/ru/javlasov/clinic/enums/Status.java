package ru.javlasov.clinic.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {

    ERROR(1L, "ERROR", "Ошибка"),
    SUCCEED(2L, "SUCCEED", "Успех"),
    PROCESS(3L, "PROCESS", "В обработке"),
    MODERATION(4L, "MODERATION", "На рассмотрении у модератора");

    private final Long id;

    private final String title;

    private final String text;

}
