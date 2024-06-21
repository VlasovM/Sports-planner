package ru.javlasov.planner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javlasov.planner.dto.ArticleDto;
import ru.javlasov.planner.model.Article;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ArticleStatusMapper.class, UserMapper.class})
public interface ArticleMapper {

    @Mapping(source = "user", target = "userDto")
    ArticleDto modelToDto(Article model);

    List<ArticleDto> modelListToDtoList(List<Article> modelList);

    @Mapping(source = "userDto", target = "user")
    Article dtoToModel(ArticleDto dto);

}
