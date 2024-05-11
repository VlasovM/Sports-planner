package ru.javlasov.sportsplanner;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ArticleStatusDto {

    VERIFICATION(1, "На проверке модератором"),
    PUBLISHED(2, "Опубликовано"),
    DECLINE(3, "Отклонено");


    private final Integer id;

    private final String title;

}
