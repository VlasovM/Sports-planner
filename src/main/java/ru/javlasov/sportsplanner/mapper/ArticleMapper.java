package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.ArticleStatusDto;
import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.model.Article;
import ru.javlasov.sportsplanner.model.ArticleStatus;

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

    default Article dtoToModel(ArticleDto dto) {
        if (dto == null) {
            return null;
        }

        Article article = new Article();
        article.setCreated(dto.getCreated());
        article.setStatus(new ArticleStatus(dto.getStatus().getId(), dto.getStatus().getTitle()));
        article.setText(dto.getText());
        article.setTitle(dto.getTitle());
        article.setUser(dto.getUser());

        return article;
    }

    List<ArticleDto> modelToDtoList(List<Article> modelList);

}
