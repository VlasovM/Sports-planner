package ru.javlasov.planner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.planner.enums.ArticleStatusDto;
import ru.javlasov.planner.model.ArticleStatus;

@Mapper(componentModel = "spring")
public interface ArticleStatusMapper {

    default ArticleStatusDto modelToDto(ArticleStatus articleStatus) {
        if (articleStatus == null) {
            return ArticleStatusDto.UNKNOWN;
        }
        return ArticleStatusDto.getById(articleStatus.getId());
    }

}
