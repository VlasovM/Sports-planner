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
import ru.javlasov.planner.controller.rest.WorkoutRestController;
import ru.javlasov.planner.dto.WorkoutDto;
import ru.javlasov.planner.security.SecurityConfig;
import ru.javlasov.planner.service.UserCredentialsService;
import ru.javlasov.planner.service.WorkoutService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkoutRestController.class)
@Import({SecurityConfig.class})
class WorkoutRestControllerTest {

    private final static String BASE_URL = "/api/v1/workout";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkoutService mockTrainService;

    @MockBean
    private UserCredentialsService mockUserCredentialsService;

    @Test
    @DisplayName("Should get all workout and get OK status")
    @WithMockUser
    void getAllWorkoutTest() throws Exception {
        List<WorkoutDto> expectedWorkoutDto = ExpectedDataFromDB.getExpectedWorkoutDtoFromDB();
        given(mockTrainService.getAllForCurrentUser()).willReturn(expectedWorkoutDto);
        mockMvc.perform(get(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(expectedWorkoutDto)));
    }

    @Test
    @WithMockUser
    @DisplayName("Should delete workout and get OK status")
    void deleteWorkoutByIdTest() throws Exception {
        // given
        var expectedDeletedId = 1L;

        // when
        mockMvc.perform(delete(BASE_URL + "/" + expectedDeletedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("Should create workout and get CREATED status")
    void createWorkoutTest() throws Exception {
        var incomeWorkoutDto = ExpectedDataFromDB.getExpectedWorkoutDtoFromDB().get(0);
        incomeWorkoutDto.setId(null);

        mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeWorkoutDto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    @DisplayName("Should edit train and get OK status")
    void editTrainTest() throws Exception {
        var incomeWorkoutDto = ExpectedDataFromDB.getExpectedWorkoutDtoFromDB().get(0);

        mockMvc.perform(patch(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeWorkoutDto))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get 302 (redirect) status if created with not auth user")
    void createTrainWithNotAuthUserTest() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

}