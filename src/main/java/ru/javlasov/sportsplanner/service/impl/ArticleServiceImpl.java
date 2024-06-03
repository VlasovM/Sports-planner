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
import ru.javlasov.sportsplanner.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    private final ArticleStatusMapper articleStatusMapper;

    private final LoggingService loggingService;

    private final UserCredentialsService userCredentialsService;

    private final UserService userService;


    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticles() {
        List<Article> allArticles = articleRepository.findAllByStatusId(ArticleStatusEnum.PUBLISHED.getId());
        List<ArticleDto> allArticlesDto = articleMapper.modelToDtoList(allArticles);
        return fillArticlesForUserFullName(allArticlesDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto getArticleById(Long id) {
        var article = findArticle(id);
        var articleDto = articleMapper.modelToDto(article);
        var user = userService.getUserById(articleDto.getUser());
        var userFullName = user.getName() + " " + user.getMiddleName() + " " + user.getSurname();
        articleDto.setUserFullName(userFullName);
        return articleDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticlesByUserId(Long userId) {
        List<Article> allArticles = articleRepository.findAllByUser(userId);
        return articleMapper.modelToDtoList(allArticles);
    }

    @Override
    @Transactional
    public void createArticle(ArticleDto articleDto) {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        articleDto.setUser(currentUser.getUser().getId());
        articleDto.setStatus(ArticleStatusEnum.VERIFICATION);
        Article entity = articleMapper.dtoToModel(articleDto);
        entity.setCreated(LocalDate.now());
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
        article.setStatus(articleStatusMapper.dtoToModel(ArticleStatusEnum.VERIFICATION));
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
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

    @Override
    public List<ArticleDto> getArticleForValidate() {
        List<Article> findArticles = articleRepository.findAllByStatusId(ArticleStatusEnum.VERIFICATION.getId());
        List<ArticleDto> allArticlesDto = articleMapper.modelToDtoList(findArticles);
        return fillArticlesForUserFullName(allArticlesDto);
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

    private List<ArticleDto> fillArticlesForUserFullName(List<ArticleDto> allArticlesDto) {
        return allArticlesDto.stream().peek(article -> {
            var user = userService.getUserById(article.getUser());
            var userFullName = user.getName() + " " + user.getMiddleName() + " " + user.getSurname();
            article.setUserFullName(userFullName);
        }).collect(Collectors.toList());
    }

}
