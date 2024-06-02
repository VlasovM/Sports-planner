package ru.javlasov.sportsplanner.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ArticleStatusEnum {

    UNKNOWN(1L, "Не определено"),
    VERIFICATION(2L, "На проверке модератором"),
    PUBLISHED(3L, "Опубликовано"),
    DECLINE(4L, "Отклонено");


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
