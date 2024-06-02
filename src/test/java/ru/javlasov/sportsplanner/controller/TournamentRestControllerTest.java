package ru.javlasov.sportsplanner.controller;

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
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.controller.rest.TournamentRestController;
import ru.javlasov.sportsplanner.security.SecurityConfig;
import ru.javlasov.sportsplanner.service.TournamentService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TournamentRestController.class)
@Import(SecurityConfig.class)
class TournamentRestControllerTest {

    private final static String BASE_URL = "/api/v1/tournaments";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TournamentService mockTournamentService;

    @MockBean
    private UserCredentialsService mockUserCredentialsService;

    @Test
    @DisplayName("Should get all tournament and get OK status")
    @WithMockUser
    void getAllTournamentsTest() throws Exception {
        given(mockTournamentService.getTournamentCurrentUser()).willReturn(ExpectedDataFromDB.getExpectedTournamentDto());
        mockMvc.perform(get(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(ExpectedDataFromDB.getExpectedTournamentDto())
                ));
    }

    @Test
    @WithMockUser
    @DisplayName("Should delete tournament and get 200 (OK) status")
    void deleteTournamentByIdTest() throws Exception {
        // given
        var expectedDeletedId = 1L;

        // when
        mockMvc.perform(delete(BASE_URL + "/" + expectedDeletedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("Should create tournament and get 201 (created) status")
    void createTournamentTest() throws Exception {
        var incomeTournamentDto = ExpectedDataFromDB.getExpectedTournamentDto().get(0);
        incomeTournamentDto.setId(null);

        mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeTournamentDto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    @DisplayName("Should update tournament and get 200 (OK) status")
    void updateTournamentTest() throws Exception {
        var incomeTournamentDto = ExpectedDataFromDB.getExpectedTournamentDto().get(0);

        mockMvc.perform(patch(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeTournamentDto))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get 302 (redirect) status if create with not auth user")
    void createTournamentWithNotAuthUserTest() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

}