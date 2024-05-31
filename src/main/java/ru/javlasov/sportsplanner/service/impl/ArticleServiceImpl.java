package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.enums.ArticleStatusEnum;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.ArticleMapper;
import ru.javlasov.sportsplanner.mapper.ArticleStatusMapper;
import ru.javlasov.sportsplanner.model.Article;
import ru.javlasov.sportsplanner.model.ArticleStatus;
import ru.javlasov.sportsplanner.repository.ArticleRepository;
import ru.javlasov.sportsplanner.service.ArticleService;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    private final ArticleStatusMapper articleStatusMapper;

    private final LoggingService loggingService;

    private final UserCredentialsService userCredentialsService;


    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticles() {
        List<Article> allArticles = articleRepository.findAllByStatusId(ArticleStatusEnum.PUBLISHED.getId());
        return articleMapper.modelToDtoList(allArticles);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto getArticleById(Long id) {
        var article = findArticle(id);
        return articleMapper.modelToDto(article);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticlesByUserId(Long userId) {
        List<Article> allArticles = articleRepository.findAllByUser(userId);
        if (allArticles.isEmpty()) {
            var errorMessage = "Не удалось найти статьи с userId = %d".formatted(userId);
            sendMessage(errorMessage, TypeMessage.ERROR);
            throw new NotFoundException(errorMessage);
        }
        return articleMapper.modelToDtoList(allArticles);
    }

    @Override
    @Transactional
    public void createArticle(ArticleDto articleDto) {
        Article entity = articleMapper.dtoToModel(articleDto);
        var newArticle = articleRepository.save(entity);
        sendMessage("Пользователь %s создал новую статью с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), newArticle.getId()), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
        sendMessage("Пользователь %s удалил статью с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), articleId), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void editArticle(ArticleDto articleDto) {
        var article = articleRepository.findById(articleDto.getId()).orElseThrow(
                () -> {
                    var errorMessage = "Статья с id = %d не найдена!".formatted(articleDto.getId());
                    sendMessage(errorMessage, TypeMessage.ERROR);
                    throw new NotFoundException(errorMessage);
                });
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setStatus(articleStatusMapper.dtoToModel(articleDto.getStatus()));
        var articleAfterSave = articleRepository.save(article);
        sendMessage("Пользователь %s изменил статью с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), articleAfterSave.getId()), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void acceptArticle(Long articleId) {
        var article = findArticle(articleId);
        article.setStatus(new ArticleStatus(ArticleStatusEnum.PUBLISHED.getId(),
                ArticleStatusEnum.PUBLISHED.getTitle()));
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void declineArticle(Long articleId) {
        var article = findArticle(articleId);
        article.setStatus(new ArticleStatus(ArticleStatusEnum.DECLINE.getId(), ArticleStatusEnum.DECLINE.getTitle()));
        articleRepository.save(article);
    }

    private Article findArticle(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(
                () -> {
                    var errorMessage = "Статья с id = %d не найдена!".formatted(articleId);
                    sendMessage(errorMessage, TypeMessage.ERROR);
                    throw new NotFoundException(errorMessage);
                });
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

}
