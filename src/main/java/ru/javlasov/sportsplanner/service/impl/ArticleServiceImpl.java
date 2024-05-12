package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.ArticleMapper;
import ru.javlasov.sportsplanner.model.Article;
import ru.javlasov.sportsplanner.repository.ArticleRepository;
import ru.javlasov.sportsplanner.service.ArticleService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;


    @Override
    public List<ArticleDto> getAllArticles() {
        List<Article> allArticles = articleRepository.findAll();
        return articleMapper.modelToDtoList(allArticles);
    }

    @Override
    public ArticleDto getArticleById(Long id) {
        var article = articleRepository.findById(id).orElseThrow(
                () -> {
                    var errorMessage = "Не найдена статья с id = %d".formatted(id);
                    log.error(errorMessage);
                    return new NotFoundException(errorMessage);
                }
        );
        return articleMapper.modelToDto(article);
    }

    @Override
    public List<ArticleDto> getAllArticlesByUserId(Long userId) {
        List<Article> allArticles = articleRepository.findAllByUser(userId);
        if (allArticles.isEmpty()) {
            var errorMessage = "Не удалось найти статьи с userId = %d".formatted(userId);
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }
        return articleMapper.modelToDtoList(allArticles);
    }

}
