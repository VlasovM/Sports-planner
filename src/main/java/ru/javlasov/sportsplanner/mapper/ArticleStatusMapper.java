package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.enums.ArticleStatusEnum;

@Mapper(componentModel = "spring")
public interface ArticleStatusMapper {

    default ru.javlasov.sportsplanner.model.ArticleStatus dtoToModel(ArticleStatusEnum dto) {
        if (dto == null) {
            return null;
        }
        return new ru.javlasov.sportsplanner.model.ArticleStatus(dto.getId(), dto.getTitle());
    }

}
