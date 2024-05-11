package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.Article;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Article repository tests")
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Should get all articles")
    void getAllArticlesTest() {
        //given
        List<Article> expectedArticles = ExpectedDataFromDB.getExpectedArticlesFromDB();

        //when
        List<Article> actualArticles = articleRepository.findAll();

        //then
        assertThat(expectedArticles.size()).isEqualTo(actualArticles.size());
        assertThat(expectedArticles.get(0).getId()).isEqualTo(actualArticles.get(0).getId());
        assertThat(expectedArticles.get(0).getStatus().getId()).isEqualTo(actualArticles.get(0).getStatus().getId());
        assertThat(expectedArticles.get(1).getId()).isEqualTo(actualArticles.get(1).getId());
        assertThat(expectedArticles.get(1).getStatus().getId()).isEqualTo(actualArticles.get(1).getStatus().getId());
    }

    @Test
    @DisplayName("Should get article by id")
    void getArticleByIdTest() {
        //given
        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().get(0);

        //when
        var actualArticle = articleRepository.findById(1L);

        //then
        assertThat(actualArticle).isPresent();
        assertThat(expectedArticle.getId()).isEqualTo(actualArticle.get().getId());
        assertThat(expectedArticle.getTitle()).isEqualTo(actualArticle.get().getTitle());
        assertThat(expectedArticle.getStatus().getId()).isEqualTo(actualArticle.get().getStatus().getId());
    }

}