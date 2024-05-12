package ru.javlasov.sportsplanner.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.dto.ArticleDto;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    @DisplayName("Should get dto from model")
    void modelToDtoTest() {
        //given
        var expectedDto = ExpectedDataFromDB.getExpectedArticleDtoFromDB().get(0);

        //when
        var actualDto = articleMapper.modelToDto(ExpectedDataFromDB.getExpectedArticlesFromDB().get(0));

        //then
        assertThat(expectedDto.getId()).isEqualTo(actualDto.getId());
        assertThat(expectedDto.getStatus()).isEqualTo(actualDto.getStatus());
        assertThat(expectedDto.getTitle()).isEqualTo(actualDto.getTitle());
    }

}