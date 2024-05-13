package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.dto.ArticleStatusDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.ArticleMapper;
import ru.javlasov.sportsplanner.mapper.ArticleStatusMapper;
import ru.javlasov.sportsplanner.model.Article;
import ru.javlasov.sportsplanner.model.ArticleStatus;
import ru.javlasov.sportsplanner.repository.ArticleRepository;
import ru.javlasov.sportsplanner.service.ArticleService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    private final ArticleStatusMapper articleStatusMapper;


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

    @Override
    public void createArticle(ArticleDto articleDto) {
        Article entity = articleMapper.dtoToModel(articleDto);
        articleRepository.save(entity);
    }

    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public void editArticle(ArticleDto articleDto) {
        var article = articleRepository.findById(articleDto.getId()).orElseThrow(
                () -> {
                    var errorMessage = "Статья с id = %d не найдена!".formatted(articleDto.getId());
                    log.error(errorMessage);
                    throw new NotFoundException(errorMessage);
                });
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setStatus(articleStatusMapper.dtoToModel(articleDto.getStatus()));
        articleRepository.save(article);
    }

    @Override
    public void acceptArticle(Long articleId) {
        var article = articleRepository.findById(articleId).orElseThrow(
                () -> {
                    var errorMessage = "Статья с id = %d не найдена!".formatted(articleId);
                    log.error(errorMessage);
                    throw new NotFoundException(errorMessage);
                });
        article.setStatus(new ArticleStatus(ArticleStatusDto.PUBLISHED.getId(), ArticleStatusDto.PUBLISHED.getTitle()));
        articleRepository.save(article);
    }

    @Override
    public void declineArticle(Long articleId) {
        var article = articleRepository.findById(articleId).orElseThrow(
                () -> {
                    var errorMessage = "Статья с id = %d не найдена!".formatted(articleId);
                    log.error(errorMessage);
                    throw new NotFoundException(errorMessage);
                });
        article.setStatus(new ArticleStatus(ArticleStatusDto.DECLINE.getId(), ArticleStatusDto.DECLINE.getTitle()));
        articleRepository.save(article);
    }

}
