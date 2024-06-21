package ru.javlasov.planner.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.model.ArticleStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    @DisplayName("Should convert model to dto")
    void modelToDtoTest() {
        // given
        var incomeArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().get(0);
        var expectedArticleDto = ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0);
        expectedArticleDto.getUserDto().setPassword(null);
        expectedArticleDto.getUserDto().setEmail(null);

        // when
        var actualArticle = articleMapper.modelToDto(incomeArticle);

        // then
        assertThat(actualArticle).usingRecursiveComparison().isEqualTo(expectedArticleDto);
    }

    @Test
    @DisplayName("Should convert dto to model")
    void dtoToModelTest() {
        // given
        var incomeArticleDto = ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0);
        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesFromDB().get(0);
        expectedArticle.setStatus(new ArticleStatus(2L, "На проверке у модератора"));

        // when
        var actualArticle = articleMapper.dtoToModel(incomeArticleDto);

        // then
        assertThat(actualArticle).usingRecursiveComparison().isEqualTo(expectedArticle);
    }

}
