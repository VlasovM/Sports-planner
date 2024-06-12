package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.model.Article;

import java.util.List;

@Mapper(componentModel = "spring", uses = ArticleStatusMapper.class)
public interface ArticleMapper {

    ArticleDto modelToDto(Article model);

    List<ArticleDto> modelListToDtoList(List<Article> modelList);

    Article dtoToModel(ArticleDto dto);

}
