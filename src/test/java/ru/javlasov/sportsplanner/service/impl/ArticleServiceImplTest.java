package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.enums.ArticleStatusEnum;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.ArticleMapper;
import ru.javlasov.sportsplanner.mapper.ArticleStatusMapper;
import ru.javlasov.sportsplanner.model.ArticleStatus;
import ru.javlasov.sportsplanner.repository.ArticleRepository;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.service.ArticleService;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class ArticleServiceImplTest {

    private final UserCredentialsRepository mockUserCredentialsRepository =
            Mockito.mock(UserCredentialsRepository.class);

    private final UserCredentialsService mockUserCredentialsService =
            Mockito.mock(UserCredentialsService.class);

    private final LoggingService mockLoggingService =
            Mockito.mock(LoggingService.class);

    private final ArticleRepository mockArticleRepository =
            Mockito.mock(ArticleRepository.class);

    private final ArticleMapper mockArticleMapper =
            Mockito.mock(ArticleMapper.class);

    private final ArticleStatusMapper mockArticleStatusMapper =
            Mockito.mock(ArticleStatusMapper.class);

    private final ArticleService underTestService = new ArticleServiceImpl(
            mockArticleRepository, mockArticleMapper, mockArticleStatusMapper,
            mockLoggingService, mockUserCredentialsService);


    @Test
    @DisplayName("Should get exception when find not exists article")
    void getNotExistsArticleTest() {
        // when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> underTestService.getArticleById(10L));

        // then
        assertThat(notFoundException.getMessage()).isEqualTo("Статья с id = 10 не найдена!");
    }

    @Test
    @DisplayName("Should edit article")
    void editArticleTest() {
        // given
        var incomeArticle = ExpectedDataFromDB.getExpectedArticleDtoFromDB().get(0);
        incomeArticle.setText("OtherText");
        incomeArticle.setTitle("OtherTitle");

        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().get(0);
        expectedArticle.setText("OtherText");
        expectedArticle.setTitle("OtherTitle");

        // when
        makeMockAuthUser();
        Mockito.when(mockArticleRepository.save(any())).thenReturn(expectedArticle);
        Mockito.when(mockArticleRepository.findById(incomeArticle.getId()))
                .thenReturn(Optional.of(expectedArticle));
        Mockito.when(mockArticleMapper.dtoToModel(incomeArticle)).thenReturn(expectedArticle);
        underTestService.editArticle(incomeArticle);

        var article = mockArticleRepository.findById(incomeArticle.getId());

        // then
        assertThat(article).isPresent();
        assertThat(incomeArticle.getTitle()).isEqualTo(article.get().getTitle());
        assertThat(incomeArticle.getText()).isEqualTo(article.get().getText());
        assertThat(article.get().getTitle()).isEqualTo("OtherTitle");
    }

    @Test
    @DisplayName("Should change status article to accept")
    void acceptArticleTest() {
        // given
        var incomeArticle = ExpectedDataFromDB.getExpectedArticleDtoFromDB().get(0);
        incomeArticle.setStatus(ArticleStatusEnum.VERIFICATION);

        // when
        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().get(0);
        expectedArticle.setStatus(new ArticleStatus(ArticleStatusEnum.VERIFICATION.getId(),
                ArticleStatusEnum.VERIFICATION.getTitle()));
        Mockito.when(mockArticleRepository.findById(incomeArticle.getId())).thenReturn(Optional.of(expectedArticle));
        Mockito.when(mockArticleRepository.save(any())).thenReturn(expectedArticle);

        underTestService.acceptArticle(incomeArticle.getId());
        var article = mockArticleRepository.findById(incomeArticle.getId());

        // then
        assertThat(article).isPresent();
        assertThat(article.get().getStatus().getTitle()).isEqualTo(ArticleStatusEnum.PUBLISHED.getTitle());
        assertThat(article.get().getStatus().getId()).isEqualTo(ArticleStatusEnum.PUBLISHED.getId());
    }

    @Test
    @DisplayName("Should change status article to decline")
    void declineArticleTest() {
        // given
        var incomeArticle = ExpectedDataFromDB.getExpectedArticleDtoFromDB().get(0);
        incomeArticle.setStatus(ArticleStatusEnum.VERIFICATION);

        // when
        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().get(0);
        expectedArticle.setStatus(new ArticleStatus(ArticleStatusEnum.DECLINE.getId(),
                ArticleStatusEnum.DECLINE.getTitle()));
        Mockito.when(mockArticleRepository.findById(incomeArticle.getId())).thenReturn(Optional.of(expectedArticle));
        Mockito.when(mockArticleRepository.save(any())).thenReturn(expectedArticle);

        underTestService.declineArticle(incomeArticle.getId());
        var article = mockArticleRepository.findById(incomeArticle.getId());

        // then
        assertThat(article).isPresent();
        assertThat(article.get().getStatus().getTitle()).isEqualTo(ArticleStatusEnum.DECLINE.getTitle());
        assertThat(article.get().getStatus().getId()).isEqualTo(ArticleStatusEnum.DECLINE.getId());
    }

    private void makeMockAuthUser() {
        var expectedAuthUser = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn(expectedAuthUser.getEmail());
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(mockUserCredentialsRepository.findByEmail(expectedAuthUser.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedAuthUser);

    }

}