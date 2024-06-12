package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.ArticleDto;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.enums.ArticleStatusDto;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.ArticleMapper;
import ru.javlasov.sportsplanner.mapper.UserMapper;
import ru.javlasov.sportsplanner.model.Article;
import ru.javlasov.sportsplanner.repository.ArticleRepository;
import ru.javlasov.sportsplanner.repository.ArticleStatusRepository;
import ru.javlasov.sportsplanner.service.ArticleService;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleStatusRepository articleStatusRepository;

    private final ArticleMapper articleMapper;

    private final UserMapper userMapper;

    private final LoggingService loggingService;

    private final UserCredentialsService userCredentialsService;

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticles() {
        List<Article> allArticles = articleRepository.findAllByStatusId(ArticleStatusDto.PUBLISHED.getId());
        return articleMapper.modelListToDtoList(allArticles);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto getArticleById(Long id) {
        var article = findArticleById(id);
        return articleMapper.modelToDto(article);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticlesByCurrentUser() {
        var user = userCredentialsService.getCurrentAuthUser().getUser();
        List<Article> allFindArticles = articleRepository.findAllByUser(user);
        return articleMapper.modelListToDtoList(allFindArticles);
    }

    @Override
    @Transactional
    public void createArticle(ArticleDto articleDto) {
        var currentUser = userCredentialsService.getCurrentAuthUser().getUser();
        var userDto = userMapper.modelToDto(currentUser);
        articleDto.setUserDto(userDto);
        articleDto.setStatus(ArticleStatusDto.VERIFICATION);
        articleDto.setCreated(LocalDate.now());
        var article = articleMapper.dtoToModel(articleDto);
        article = articleRepository.save(article);
        sendMessage("Пользователь %s создал новую статью с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), article.getId()), TypeMessage.INFO);
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
        articleDto.setStatus(ArticleStatusDto.VERIFICATION);
        var article = findArticleById(articleDto.getId());
        article = articleRepository.save(article);
        sendMessage("Пользователь %s изменил статью с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), article.getId()), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void acceptArticle(Long articleId) {
        var article = findArticleById(articleId);
        var publishedStatus = articleStatusRepository
                .findById(ArticleStatusDto.PUBLISHED.getId()).orElseThrow();
        article.setStatus(publishedStatus);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void declineArticle(Long articleId) {
        var article = findArticleById(articleId);
        var declineStatus = articleStatusRepository
                .findById(ArticleStatusDto.PUBLISHED.getId()).orElseThrow();
        article.setStatus(declineStatus);
        articleRepository.save(article);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getArticleForValidate() {
        List<Article> findArticles = articleRepository.findAllByStatusId(ArticleStatusDto.VERIFICATION.getId());
        return articleMapper.modelListToDtoList(findArticles);
    }

    private Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> {
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
