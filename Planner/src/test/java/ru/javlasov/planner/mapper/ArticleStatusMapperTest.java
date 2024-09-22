package ru.javlasov.planner.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.enums.ArticleStatusDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
public class ArticleStatusMapperTest {

    @Autowired
    private ArticleStatusMapper articleStatusMapper;

    @Test
    @DisplayName("Should convert model to dto")
    void modelToDtoTest() {
        // given
        var expectedArticleStatusDto = ArticleStatusDto.PUBLISHED;
        var expectedIncomeArticle = ExpectedDataFromDB.getExpectedArticlesStatusFromDB()
                .stream().filter(articleStatus -> ArticleStatusDto.PUBLISHED.getId().equals(articleStatus.getId()))
                .findFirst().orElse(null);

        // when
        var actualArticleStatusDto = articleStatusMapper.modelToDto(expectedIncomeArticle);

        // then
        assertThat(actualArticleStatusDto).isNotNull();
        assertThat(expectedArticleStatusDto).usingRecursiveComparison().isEqualTo(actualArticleStatusDto);
    }

    @Test
    @DisplayName("Should get UNKNOWN status")
    void modelToDtoIfIncomeValueIsNull() {
        // given
        var expectedArticleStatusDto = ArticleStatusDto.UNKNOWN;

        // when
        var actualArticleDto = articleStatusMapper.modelToDto(null);

        assertThat(actualArticleDto).isNotNull();
        assertThat(expectedArticleStatusDto).usingRecursiveComparison().isEqualTo(actualArticleDto);
    }

}
