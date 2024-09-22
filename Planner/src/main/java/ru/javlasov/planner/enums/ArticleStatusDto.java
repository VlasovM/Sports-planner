package ru.javlasov.planner.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ArticleStatusDto {

    UNKNOWN(1L, "Не определено"),
    VERIFICATION(2L, "На проверке у модератора"),
    PUBLISHED(3L, "Опубликовано"),
    DECLINE(4L, "Отклонено");


    private final Long id;

    private final String title;

    public static ArticleStatusDto getById(Long id) {
        for (ArticleStatusDto statusDto : values()) {
            if (statusDto.id.equals(id)) {
                return statusDto;
            }
        }
        return UNKNOWN;
    }

}
