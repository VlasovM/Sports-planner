package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.ArticleStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Article status repository test")
class ArticleStatusRepositoryTest {

    @Autowired
    private ArticleStatusRepository articleStatusRepository;

    @Test
    @DisplayName("Should get all articles status")
    void getAllArticlesStatus() {
        //given
        List<ArticleStatus> expectedArticlesStatus = ExpectedDataFromDB.getExpectedArticlesStatusFromDB();

        //when
        List<ArticleStatus> actualArticlesStatus = articleStatusRepository.findAll();

        //then
        assertThat(expectedArticlesStatus.size()).isEqualTo(actualArticlesStatus.size());
        assertThat(expectedArticlesStatus.get(0).getId()).isEqualTo(actualArticlesStatus.get(0).getId());
        assertThat(expectedArticlesStatus.get(0).getTitle()).isEqualTo(actualArticlesStatus.get(0).getTitle());
        assertThat(expectedArticlesStatus.get(1).getId()).isEqualTo(actualArticlesStatus.get(1).getId());
        assertThat(expectedArticlesStatus.get(1).getTitle()).isEqualTo(actualArticlesStatus.get(1).getTitle());
    }

    @Test
    @DisplayName("Should get article status by id")
    void getArticleStatusById() {
        //given
        var expectedArticleStatus = ExpectedDataFromDB.getExpectedArticlesStatusFromDB().get(0);

        //when
        var actualArticleStatus = articleStatusRepository.findById(1L);

        //then
        assertThat(actualArticleStatus).isPresent();
        assertThat(expectedArticleStatus.getId()).isEqualTo(actualArticleStatus.get().getId());
        assertThat(expectedArticleStatus.getTitle()).isEqualTo(actualArticleStatus.get().getTitle());
    }


}