package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.ArticleStatusDto;
import ru.javlasov.sportsplanner.model.ArticleStatus;

@Mapper(componentModel = "spring")
public interface ArticleStatusMapper {

    default ArticleStatus dtoToModel(ArticleStatusDto dto) {
        if (dto == null) {
            return null;
        }
        return new ArticleStatus(dto.getId(), dto.getTitle());
    }

}
