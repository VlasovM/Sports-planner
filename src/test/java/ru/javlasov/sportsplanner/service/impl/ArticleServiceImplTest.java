package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.service.ArticleService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    @DisplayName("Should get exception when find not exists article")
    void getNotExistsArticleTest() {
        //when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> articleService.getArticleById(10L));

        //then
        assertThat(notFoundException.getMessage()).isEqualTo("Не найдена статья с id = 10");
    }

}