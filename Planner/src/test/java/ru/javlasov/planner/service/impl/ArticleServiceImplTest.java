package ru.javlasov.planner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.dto.ArticleDto;
import ru.javlasov.planner.enums.ArticleStatusDto;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.mapper.ArticleMapper;
import ru.javlasov.planner.mapper.UserMapper;
import ru.javlasov.planner.model.Article;
import ru.javlasov.planner.model.ArticleStatus;
import ru.javlasov.planner.model.UserCredentials;
import ru.javlasov.planner.repository.ArticleRepository;
import ru.javlasov.planner.repository.ArticleStatusRepository;
import ru.javlasov.planner.repository.UserCredentialsRepository;
import ru.javlasov.planner.service.ArticleService;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.UserCredentialsService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class ArticleServiceImplTest {

    private final ArticleRepository mockArticleRepository = Mockito.mock(ArticleRepository.class);

    private final ArticleStatusRepository mockArticleStatusRepository = Mockito.mock(ArticleStatusRepository.class);

    private final ArticleMapper mockArticleMapper = Mockito.mock(ArticleMapper.class);

    private final UserMapper mockUserMapper = Mockito.mock(UserMapper.class);

    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);

    private final LoggingService mockLoggingService = Mockito.mock(LoggingService.class);

    private final UserCredentialsRepository mockUserCredentialsRepository
            = Mockito.mock(UserCredentialsRepository.class);

    private final ArticleService underTestService = new ArticleServiceImpl(mockArticleRepository,
            mockArticleStatusRepository, mockArticleMapper, mockUserMapper, mockLoggingService,
            mockUserCredentialsService);

    @Test
    @DisplayName("Should get all articles")
    void getAllArticlesTest() {
        // given
        List<Article> expectedArticles = ExpectedDataFromDB.getExpectedArticlesFromDB()
                .stream().filter(article -> ArticleStatusDto.PUBLISHED.getId().equals(article.getStatus().getId()))
                .toList();

        // when
        List<ArticleDto> expectedArticlesDto = ExpectedDataFromDB.getExpectedArticlesDtoFromDB()
                .stream().filter(articleDto -> ArticleStatusDto.PUBLISHED.equals(articleDto.getStatus())).toList();

        Mockito.when(mockArticleRepository.findAllByStatusId(ArticleStatusDto.PUBLISHED.getId()))
                .thenReturn(expectedArticles);
        Mockito.when(mockArticleMapper.modelListToDtoList(expectedArticles)).thenReturn(expectedArticlesDto);

        List<ArticleDto> actualArticlesDto = underTestService.getAllArticles();

        // then
        assertThat(expectedArticlesDto.size()).isEqualTo(actualArticlesDto.size());
        assertThat(expectedArticlesDto).usingRecursiveComparison().isEqualTo(actualArticlesDto);
    }

    @Test
    @DisplayName("Should get article by id")
    void getArticleById() {
        // given
        var expectedArticle = Optional.of(ExpectedDataFromDB.getExpectedArticlesFromDB().get(0));

        // when
        var expectedArticleDto = ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0);

        Mockito.when(mockArticleRepository.findById(expectedArticle.get().getId())).thenReturn(expectedArticle);
        Mockito.when(mockArticleMapper.modelToDto(expectedArticle.get())).thenReturn(expectedArticleDto);

        var actualArticleDto = underTestService.getArticleById(expectedArticle.get().getId());

        // then
        assertThat(actualArticleDto).isNotNull();
        assertThat(actualArticleDto).usingRecursiveComparison().isEqualTo(expectedArticleDto);
    }

    @Test
    @DisplayName("Should get articles for current user")
    void getArticlesForCurrentUserTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var expectedUser = expectedUserCredentials.getUser();
        var expectedArticles = ExpectedDataFromDB.getExpectedArticlesFromDB();
        var expectedArticlesDto = ExpectedDataFromDB.getExpectedArticlesDtoFromDB();

        // when
        makeMockAuthUser(expectedUserCredentials);

        Mockito.when(mockArticleRepository.findAllByUser(expectedUser)).thenReturn(expectedArticles);
        Mockito.when(mockArticleMapper.modelListToDtoList(expectedArticles)).thenReturn(expectedArticlesDto);

        var actualArticlesDto = underTestService.getAllArticlesByCurrentUser();

        // then
        assertThat(actualArticlesDto).isNotNull();
        assertThat(actualArticlesDto).usingRecursiveComparison().isEqualTo(expectedArticlesDto);
    }

    @Test
    @DisplayName("Should edit article")
    void editArticleTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var incomeArticle = ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0);
        incomeArticle.setText("OtherText");
        incomeArticle.setTitle("OtherTitle");

        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().get(0);
        expectedArticle.setTitle(incomeArticle.getTitle());
        expectedArticle.setText(incomeArticle.getText());

        // when
        makeMockAuthUser(expectedUserCredentials);

        Mockito.when(mockArticleRepository.save(any())).thenReturn(expectedArticle);
        Mockito.when(mockArticleRepository.findById(incomeArticle.getId()))
                .thenReturn(Optional.of(expectedArticle));
        Mockito.when(mockArticleMapper.dtoToModel(incomeArticle)).thenReturn(expectedArticle);
        underTestService.editArticle(incomeArticle);

        var actualArticle = mockArticleRepository.findById(incomeArticle.getId());

        // then
        assertThat(actualArticle).isPresent();
        assertThat(actualArticle.get()).usingRecursiveComparison().isEqualTo(expectedArticle);
    }

    @Test
    @DisplayName("Should change status article to accept")
    void acceptArticleTest() {
        // given
        var incomeArticle = ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0);
        incomeArticle.setStatus(ArticleStatusDto.VERIFICATION);

        // when
        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().get(0);
        expectedArticle.setStatus(new ArticleStatus(ArticleStatusDto.VERIFICATION.getId(),
                ArticleStatusDto.VERIFICATION.getTitle()));
        Mockito.when(mockArticleRepository.findById(incomeArticle.getId())).thenReturn(Optional.of(expectedArticle));
        Mockito.when(mockArticleStatusRepository.findById(ArticleStatusDto.PUBLISHED.getId())).thenReturn(Optional.of(
                ExpectedDataFromDB.getExpectedArticlesStatusFromDB().get(0)));
        Mockito.when(mockArticleRepository.save(any())).thenReturn(expectedArticle);

        underTestService.acceptArticle(incomeArticle.getId());
        var actualArticle = mockArticleRepository.findById(incomeArticle.getId());

        // then
        assertThat(actualArticle).isPresent();
        assertThat(actualArticle.get()).usingRecursiveComparison().isEqualTo(expectedArticle);
    }

    @Test
    @DisplayName("Should change status article to decline")
    void declineArticleTest() {
        // given
        var incomeArticle = ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0);
        incomeArticle.setStatus(ArticleStatusDto.VERIFICATION);

        // when
        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().get(0);
        expectedArticle.setStatus(new ArticleStatus(ArticleStatusDto.DECLINE.getId(),
                ArticleStatusDto.DECLINE.getTitle()));
        Mockito.when(mockArticleRepository.findById(incomeArticle.getId())).thenReturn(Optional.of(expectedArticle));
        Mockito.when(mockArticleStatusRepository.findById(ArticleStatusDto.DECLINE.getId())).thenReturn(Optional.of(
                ExpectedDataFromDB.getExpectedArticlesStatusFromDB().get(3)));
        Mockito.when(mockArticleRepository.save(any())).thenReturn(expectedArticle);

        underTestService.declineArticle(incomeArticle.getId());
        var actualArticle = mockArticleRepository.findById(incomeArticle.getId());

        // then
        assertThat(actualArticle).isPresent();
        assertThat(actualArticle.get()).usingRecursiveComparison().isEqualTo(expectedArticle);
    }

    @Test
    @DisplayName("Should get all articles for validate moderator")
    void getArticleForValidateTest() {
        // given
        List<Article> expectedArticles = ExpectedDataFromDB.getExpectedArticlesFromDB()
                .stream()
                .filter(article -> ArticleStatusDto.VERIFICATION.getId().equals(article.getStatus().getId()))
                .toList();
        List<ArticleDto> expectedArticleDto = ExpectedDataFromDB.getExpectedArticlesDtoFromDB()
                .stream()
                .filter(articleDto -> ArticleStatusDto.VERIFICATION.equals(articleDto.getStatus()))
                .toList();

        // then
        Mockito.when(mockArticleRepository.findAllByStatusId(ArticleStatusDto.VERIFICATION.getId()))
                .thenReturn(expectedArticles);
        Mockito.when(mockArticleMapper.modelListToDtoList(expectedArticles)).thenReturn(expectedArticleDto);
        List<ArticleDto> actualArticleDto = underTestService.getArticleForValidate();

        // when
        assertThat(actualArticleDto).isNotEmpty();
        assertThat(actualArticleDto.size()).isEqualTo(expectedArticleDto.size());
        assertThat(actualArticleDto).usingRecursiveComparison().isEqualTo(expectedArticleDto);
    }

    @Test
    @DisplayName("Should get exception when article not found by id")
    void findArticleByNotExistsIdTest() {
        // given
        var incomeId = 10L;

        // when
        Mockito.when(mockArticleRepository.findById(incomeId)).thenReturn(Optional.empty());
        NotFoundException actualResult = assertThrows(NotFoundException.class,
                () -> underTestService.getArticleById(incomeId));


        // then
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getMessage()).isEqualTo("Статья с id = 10 не найдена!");
    }

    private void makeMockAuthUser(UserCredentials expectedUserCredentials) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn(expectedUserCredentials.getEmail());
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(mockUserCredentialsRepository.findByEmail(expectedUserCredentials.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedUserCredentials);
    }

}