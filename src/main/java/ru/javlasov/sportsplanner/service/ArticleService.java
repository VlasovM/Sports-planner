package ru.javlasov.sportsplanner.service;

import ru.javlasov.sportsplanner.dto.ArticleDto;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getAllArticles();

    ArticleDto getArticleById(Long id);

    List<ArticleDto> getAllArticlesByCurrentUser();

    void createArticle(ArticleDto articleDto);

    void deleteArticle(Long articleId);

    void acceptArticle(Long articleId);

    void declineArticle(Long articleId);

    void editArticle(ArticleDto articleDto);

    List<ArticleDto> getArticleForValidate();

}
