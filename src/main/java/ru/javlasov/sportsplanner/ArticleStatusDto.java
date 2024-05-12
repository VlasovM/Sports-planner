package ru.javlasov.sportsplanner;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ArticleStatusDto {

    UNKNOWN(0L, "Не определено"),
    VERIFICATION(1L, "На проверке модератором"),
    PUBLISHED(2L, "Опубликовано"),
    DECLINE(3L, "Отклонено");


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
