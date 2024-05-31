package ru.javlasov.sportsplanner.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TypeMessage {

    INFO(1L, "Информация"),
    ERROR(2L, "Ошибка"),
    DEBUG(3L, "Для дебага");

    private final Long id;

    private final String title;

}
