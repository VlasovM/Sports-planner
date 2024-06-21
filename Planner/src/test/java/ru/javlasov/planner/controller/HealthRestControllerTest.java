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
import ru.javlasov.planner.controller.rest.HealthRestController;
import ru.javlasov.planner.dto.HealthDto;
import ru.javlasov.planner.security.SecurityConfig;
import ru.javlasov.planner.service.HealthService;
import ru.javlasov.planner.service.UserCredentialsService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthRestController.class)
@Import(SecurityConfig.class)
class HealthRestControllerTest {

    private final static String BASE_URL = "/api/v1/healths";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HealthService mockHealthService;

    @MockBean
    private UserCredentialsService mockUserCredentialsService;


    @Test
    @DisplayName("Should get all healths")
    @WithMockUser
    void getAllHealthForCurrentUserTest() throws Exception {
        List<HealthDto> expectedHealthDtoList = ExpectedDataFromDB.getExpectedHealthDtoFromDB();
        given(mockHealthService.getHealthCurrentUser()).willReturn(expectedHealthDtoList);
        mockMvc.perform(get(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedHealthDtoList)
                ));
    }

    @Test
    @DisplayName("Should delete health by id and get OK status")
    @WithMockUser
    void deleteHealthByIdTest() throws Exception {
        // given
        var expectedDeletedId = 1L;

        // when
        mockMvc.perform(delete(BASE_URL + "/" + expectedDeletedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get 302 (redirect) status if create with not auth user")
    void createHealthWithNotAuthUserTest() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    @DisplayName("Should update health and get ok status")
    void updateHealthTest() throws Exception {
        var incomeHealthDto = ExpectedDataFromDB.getExpectedHealthDtoFromDB().get(0);

        mockMvc.perform(patch(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeHealthDto))
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("Should create health and get created status")
    void createHealthTest() throws Exception {
        var incomeHealthDto = ExpectedDataFromDB.getExpectedHealthDtoFromDB().get(0);
        incomeHealthDto.setId(null);

        mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeHealthDto))
                )
                .andExpect(status().isCreated());
    }

}