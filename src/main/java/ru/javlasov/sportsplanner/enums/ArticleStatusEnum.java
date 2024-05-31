package ru.javlasov.sportsplanner.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ArticleStatusEnum {

    UNKNOWN(0L, "Не определено"),
    VERIFICATION(1L, "На проверке модератором"),
    PUBLISHED(2L, "Опубликовано"),
    DECLINE(3L, "Отклонено");


    private final Long id;

    private final String title;

    public static ArticleStatusEnum getById(Long id) {
        for (ArticleStatusEnum statusDto : values()) {
            if (statusDto.id.equals(id)) {
                return statusDto;
            }
        }
        return UNKNOWN;
    }

}
