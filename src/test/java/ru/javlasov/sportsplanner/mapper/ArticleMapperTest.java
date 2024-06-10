//package ru.javlasov.sportsplanner.mapper;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.javlasov.sportsplanner.ExpectedDataFromDB;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
//class ArticleMapperTest {
//
//    @Autowired
//    private ArticleMapper articleMapper;
//
//    @Test
//    @DisplayName("Should get dto from model")
//    void modelToDtoTest() {
//        //given
//        var expectedDto = ExpectedDataFromDB.getExpectedArticleDtoFromDB().get(0);
//        var expectedArticle = ExpectedDataFromDB.getExpectedArticlesSetFromDB().stream()
//                .filter(article -> article.getId().equals(expectedDto.getId()))
//                .findFirst().orElseThrow();
//
//        //when
//        var actualDto = articleMapper.modelToDto(expectedArticle);
//
//        //then
//        assertThat(expectedDto.getId()).isEqualTo(actualDto.getId());
//        assertThat(expectedDto.getStatus()).isEqualTo(actualDto.getStatus());
//        assertThat(expectedDto.getTitle()).isEqualTo(actualDto.getTitle());
//    }
//
//}