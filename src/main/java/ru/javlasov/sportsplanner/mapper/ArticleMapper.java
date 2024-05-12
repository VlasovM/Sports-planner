package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.ArticleStatusDto;
import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.model.Article;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    default ArticleDto modelToDto(Article model) {

        if (model == null) {
            return null;
        }

        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(model.getId());
        articleDto.setCreated(model.getCreated());
        articleDto.setTitle(model.getTitle());
        articleDto.setText(model.getText());
        articleDto.setUser(model.getUser());
        articleDto.setStatus(ArticleStatusDto.getById(model.getStatus().getId()));

        return articleDto;
    }

    List<ArticleDto> modelToDtoList(List<Article> modelList);

}
