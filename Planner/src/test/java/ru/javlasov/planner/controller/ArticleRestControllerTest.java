package ru.javlasov.planner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.controller.rest.ArticleRestController;
import ru.javlasov.planner.dto.ArticleDto;
import ru.javlasov.planner.dto.ErrorMessageDto;
import ru.javlasov.planner.enums.ArticleStatusDto;
import ru.javlasov.planner.security.SecurityConfig;
import ru.javlasov.planner.service.ArticleService;
import ru.javlasov.planner.service.UserCredentialsService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleRestController.class)
@Import({SecurityConfig.class})
public class ArticleRestControllerTest {

    private final static String BASE_URL = "/api/v1/articles";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleService mockArticleService;

    @MockBean
    private UserCredentialsService mockUserCredentialsService;

    @Test
    @DisplayName("Should get OK status and list of articles")
    void getAllArticlesTest() throws Exception {
        given(mockArticleService.getAllArticles()).willReturn(ExpectedDataFromDB.getExpectedArticlesDtoFromDB());
        mockMvc.perform(get(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(ExpectedDataFromDB.getExpectedArticlesDtoFromDB())));
    }

    @Test
    @DisplayName("Should get OK status and article by id")
    @WithMockUser
    void getArticleByIdTest() throws Exception {
        String findArticleId = "1";
        given(mockArticleService.getArticleById(1L)).willReturn(ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0));
        mockMvc.perform(get(BASE_URL + "/article/" + findArticleId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0))));
    }

    @Test
    @DisplayName("Should get 302 (redirect) status if create with not auth user")
    void createArticleWithNotAuthUserTest() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Should create article and get 201 (created) status")
    @WithMockUser
    void createArticleTest() throws Exception {
        var incomeArticleDto = ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0);
        incomeArticleDto.setId(null);

        mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeArticleDto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    @DisplayName("Should get BAD REQUEST if invalid income article when create")
    void attemptCreateInvalidArticleTest() throws Exception {
        // given
        var expectedUserDto = ExpectedDataFromDB.getExpectedUsersDtoFromDB().get(0);
        var expectedStatus = 400;
        var incomeInvalidDto = new ArticleDto(null, ArticleStatusDto.VERIFICATION, null, null,
                LocalDate.now(), expectedUserDto);

        // when
        var resultActions = mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeInvalidDto))
                )
                .andExpect(status().isBadRequest());
        var actualStatus = resultActions.andReturn().getResponse().getStatus();

        // then
        assertThat(actualStatus).isEqualTo(expectedStatus);
    }

    @Test
    @DisplayName("Should delete article by id and get status OK")
    @WithMockUser
    void deleteArticleByIdTest() throws Exception {
        // given
        var expectedDeletedId = 1L;

        // when
        mockMvc.perform(delete(BASE_URL + "/" + expectedDeletedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should accept article and get status OK")
    void acceptArticleTest() throws Exception {
        // given
        var expectedDeletedId = 1L;

        // when
        mockMvc.perform(patch(BASE_URL + "/accept/" + expectedDeletedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should decline article and get status OK")
    void declineArticleTest() throws Exception {
        // given
        var expectedDeletedId = 1L;

        // when
        mockMvc.perform(patch(BASE_URL + "/decline/" + expectedDeletedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("Should get 403 status (Forbidden)")
    void declineArticleWithIncorrectUserTest() throws Exception {
        // given
        var expectedDeletedId = 1L;

        // when
        mockMvc.perform(patch(BASE_URL + "/decline/" + expectedDeletedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should update article and get 200 (ок) status")
    @WithMockUser
    void updateArticleTest() throws Exception {
        var incomeArticleDto = ExpectedDataFromDB.getExpectedArticlesDtoFromDB().get(0);
        incomeArticleDto.setId(null);

        mockMvc.perform(patch(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeArticleDto))
                )
                .andExpect(status().isOk());
    }

}