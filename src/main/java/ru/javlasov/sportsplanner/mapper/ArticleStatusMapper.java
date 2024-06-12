package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.enums.ArticleStatusDto;
import ru.javlasov.sportsplanner.model.ArticleStatus;

@Mapper(componentModel = "spring")
public interface ArticleStatusMapper {

    default ArticleStatusDto modelToDto(ArticleStatus articleStatus) {
        if (articleStatus == null) {
            return ArticleStatusDto.UNKNOWN;
        }
        return ArticleStatusDto.getById(articleStatus.getId());
    }

}
