package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.enums.ArticleStatusEnum;
import ru.javlasov.sportsplanner.model.Article;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Article repository tests")
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Should get all articles")
    void getAllArticlesTest() {
        // given
        Set<Article> expectedArticlesSet = ExpectedDataFromDB.getExpectedArticlesFromDB();
        List<Article> expectedArticlesList = new ArrayList<>(expectedArticlesSet);
        expectedArticlesList = expectedArticlesList
                .stream()
                .sorted(Comparator.comparing(Article::getId)).
                collect(Collectors.toList());

        // when
        List<Article> actualArticles = articleRepository.findAll();

        // then
        assertThat(expectedArticlesList.size()).isEqualTo(actualArticles.size());
        assertThat(expectedArticlesList.get(0).getId()).isEqualTo(actualArticles.get(0).getId());
        assertThat(expectedArticlesList.get(0).getStatus().getId()).isEqualTo(actualArticles.get(0).getStatus().getId());
        assertThat(expectedArticlesList.get(1).getId()).isEqualTo(actualArticles.get(1).getId());
        assertThat(expectedArticlesList.get(1).getStatus().getId()).isEqualTo(actualArticles.get(1).getStatus().getId());
    }

    @Test
    @DisplayName("Should get article by id")
    void getArticleByIdTest() {
        // given
        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().stream()
                .filter(article -> article.getId().equals(1L))
                .findFirst().orElseThrow();

        // when
        var actualArticle = articleRepository.findById(1L);

        // then
        assertThat(actualArticle).isPresent();
        assertThat(expectedArticle.getId()).isEqualTo(actualArticle.get().getId());
        assertThat(expectedArticle.getTitle()).isEqualTo(actualArticle.get().getTitle());
        assertThat(expectedArticle.getStatus().getId()).isEqualTo(actualArticle.get().getStatus().getId());
    }

    @Test
    @DisplayName("Should get all articles by user id")
    void getArticlesByUserIdTest() {
        // given
        Set<Article> expectedArticlesSet = ExpectedDataFromDB.getExpectedArticlesFromDB();
        List<Article> expectedArticlesList = new ArrayList<>(expectedArticlesSet);
        expectedArticlesList = expectedArticlesList
                .stream()
                .sorted(Comparator.comparing(Article::getId)).
                collect(Collectors.toList());

        // when
        List<Article> actualArticles = articleRepository.findAllByUser(1L);

        // then
        assertThat(expectedArticlesList.size()).isEqualTo(actualArticles.size());
        assertThat(expectedArticlesList.get(0).getId()).isEqualTo(actualArticles.get(0).getId());
        assertThat(expectedArticlesList.get(0).getStatus().getId()).isEqualTo(actualArticles.get(0).getStatus().getId());
        assertThat(expectedArticlesList.get(1).getId()).isEqualTo(actualArticles.get(1).getId());
        assertThat(expectedArticlesList.get(1).getStatus().getId()).isEqualTo(actualArticles.get(1).getStatus().getId());
    }

    @Test
    @DisplayName("Should get empty articles by user id")
    void getEmptyArticlesByUserIdTest() {
        // when
        List<Article> actualArticles = articleRepository.findAllByUser(2L);

        // then
        assertThat(actualArticles).isEmpty();

    }

    @Test
    @DisplayName("Should save new article")
    void saveArticleTest() {
        // given
        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().stream()
                .filter(article -> article.getId().equals(1L))
                .findFirst().orElseThrow();
        expectedArticle.setId(null);

        // when
        var actualArticle = articleRepository.save(expectedArticle);

        // then
        assertThat(actualArticle).isNotNull();
        assertThat(actualArticle.getId()).isNotNull();
        assertThat(expectedArticle.getTitle()).isEqualTo(actualArticle.getTitle());
        assertThat(expectedArticle.getText()).isEqualTo(actualArticle.getText());
    }

    @Test
    @DisplayName("Should delete article")
    void deleteArticleTest() {
        // given
        var existsArticle = articleRepository.findById(1L);
        assertThat(existsArticle).isPresent();

        // when
        articleRepository.deleteById(existsArticle.get().getId());

        //then
        var articleAfterDelete = articleRepository.findById(1L);

        assertThat(articleAfterDelete).isEmpty();
    }

    @Test
    @DisplayName("Should edit article")
    void editArticleTest() {
        // given
        var existsArticle = articleRepository.findById(1L);
        assertThat(existsArticle).isPresent();

        // when
        existsArticle.get().setTitle("Other title");
        var actualArticle = articleRepository.save(existsArticle.get());


        // then
        assertThat(actualArticle).isNotNull();
        assertThat(existsArticle.get().getTitle()).isEqualTo(actualArticle.getTitle());
    }

    @Test
    @DisplayName("Should get all articles by status")
    void getAllArticlesByStatusTest() {
        // given
        List<Article> expectedArticles = ExpectedDataFromDB.getExpectedArticlesFromDB()
                .stream().filter(article -> article.getStatus().getId()
                        .equals(ArticleStatusEnum.PUBLISHED.getId())).toList();

        // when
        List<Article> actualArticles = articleRepository.findAllByStatusId(ArticleStatusEnum.PUBLISHED.getId());

        // then
        assertThat(expectedArticles.size()).isEqualTo(actualArticles.size());
        assertThat(expectedArticles.get(0).getStatus().getId()).isEqualTo(actualArticles.get(0).getStatus().getId());
    }

}