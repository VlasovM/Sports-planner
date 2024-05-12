package ru.javlasov.sportsplanner.service;

import ru.javlasov.sportsplanner.dto.ArticleDto;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getAllArticles();

    ArticleDto getArticleById(Long id);

    List<ArticleDto> getAllArticlesByUserId(Long userId);

}
