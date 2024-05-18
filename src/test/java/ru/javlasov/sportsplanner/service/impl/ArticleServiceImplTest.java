package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.dto.ArticleStatusDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.repository.ArticleRepository;
import ru.javlasov.sportsplanner.service.ArticleService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Should get exception when find not exists article")
    void getNotExistsArticleTest() {
        // when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> articleService.getArticleById(10L));

        // then
        assertThat(notFoundException.getMessage()).isEqualTo("Не найдена статья с id = 10");
    }

    @Test
    @DisplayName("Should edit article")
    void editArticleTest() {
        // given
        var incomeArticle = ExpectedDataFromDB.getExpectedArticleDtoFromDB().get(0);
        incomeArticle.setText("OtherText");
        incomeArticle.setTitle("OtherTitle");

        // when
        articleService.editArticle(incomeArticle);
        var article = articleRepository.findById(incomeArticle.getId());

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
        incomeArticle.setStatus(ArticleStatusDto.VERIFICATION);

        // when
        articleService.acceptArticle(incomeArticle.getId());
        var article = articleRepository.findById(incomeArticle.getId());

        // then
        assertThat(article).isPresent();
        assertThat(article.get().getStatus().getTitle()).isEqualTo(ArticleStatusDto.PUBLISHED.getTitle());
        assertThat(article.get().getStatus().getId()).isEqualTo(ArticleStatusDto.PUBLISHED.getId());
    }

    @Test
    @DisplayName("Should change status article to decline")
    void declineArticleTest() {
        // given
        var incomeArticle = ExpectedDataFromDB.getExpectedArticleDtoFromDB().get(0);
        incomeArticle.setStatus(ArticleStatusDto.VERIFICATION);

        // when
        articleService.declineArticle(incomeArticle.getId());
        var article = articleRepository.findById(incomeArticle.getId());

        // then
        assertThat(article).isPresent();
        assertThat(article.get().getStatus().getTitle()).isEqualTo(ArticleStatusDto.DECLINE.getTitle());
        assertThat(article.get().getStatus().getId()).isEqualTo(ArticleStatusDto.DECLINE.getId());
    }

}