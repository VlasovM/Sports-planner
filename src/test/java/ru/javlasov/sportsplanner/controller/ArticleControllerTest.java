package ru.javlasov.sportsplanner.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleService mockArticleService;

    @Test
    @DisplayName("Should get OK status and List of articles")
    void getAllArticlesTest() throws Exception {
        given(mockArticleService.getAllArticles()).willReturn(ExpectedDataFromDB.getExpectedArticleDtoFromDB());
        var content = mockMvc.perform(get("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(ExpectedDataFromDB.getExpectedArticleDtoFromDB())));
        System.out.println(content.andReturn());
    }

    @Test
    void getArticleById() {
    }

    @Test
    void createArticle() {
    }

    @Test
    void deleteArticleById() {
    }

    @Test
    void acceptArticle() {
    }

    @Test
    void declineArticle() {
    }

}