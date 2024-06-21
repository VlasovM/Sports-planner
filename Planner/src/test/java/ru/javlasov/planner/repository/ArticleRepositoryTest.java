package ru.javlasov.planner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.enums.ArticleStatusDto;
import ru.javlasov.planner.model.Article;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Should get all articles by user id")
    void getArticlesByUserIdTest() {
        // given
        List<Article> expectedArticles = ExpectedDataFromDB.getExpectedArticlesFromDB();
        var expectedUser = ExpectedDataFromDB.getExpectedUsersFromDB().get(0);

        // when
        List<Article> actualArticles = articleRepository.findAllByUser(expectedUser);

        // then
        assertThat(expectedArticles.size()).isEqualTo(actualArticles.size());
        assertThat(expectedArticles).usingRecursiveComparison().isEqualTo(actualArticles);
    }

    @Test
    @DisplayName("Should get all articles by status")
    void getAllArticlesByStatusTest() {
        // given
        List<Article> expectedArticles = ExpectedDataFromDB.getExpectedArticlesFromDB()
                .stream()
                .filter(article -> article.getStatus().getId().equals(ArticleStatusDto.PUBLISHED.getId()))
                .toList();

        // when
        List<Article> actualArticles = articleRepository.findAllByStatusId(ArticleStatusDto.PUBLISHED.getId());

        // then
        assertThat(expectedArticles.size()).isEqualTo(actualArticles.size());
        assertThat(expectedArticles).usingRecursiveComparison().isEqualTo(actualArticles);
    }

}