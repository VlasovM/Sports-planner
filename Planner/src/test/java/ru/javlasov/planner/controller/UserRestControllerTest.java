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
import ru.javlasov.planner.controller.rest.UserRestController;
import ru.javlasov.planner.security.SecurityConfig;
import ru.javlasov.planner.service.UserCredentialsService;
import ru.javlasov.planner.service.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@Import(SecurityConfig.class)
class UserRestControllerTest {

    private final static String BASE_URL = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService mockUserService;

    @MockBean
    private UserCredentialsService mockUserCredentialsService;

    @Test
    @DisplayName("Should get all users and get OK status")
    @WithMockUser
    void getUserInformation() throws Exception {
        var expectedUserDto = ExpectedDataFromDB.getExpectedUsersDtoFromDB().get(0);
        given(mockUserService.getInfoAuthorizedUser()).willReturn(expectedUserDto);
        mockMvc.perform(get(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(expectedUserDto)));
    }

    @Test
    @DisplayName("Should register user and get 201 CREATED status")
    void registration() throws Exception {
        var incomeUserDto = ExpectedDataFromDB.getExpectedUsersDtoFromDB().get(0);
        incomeUserDto.setId(null);

        mockMvc.perform(post(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeUserDto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    @DisplayName("Should edit profile and get OK status")
    void editProfile() throws Exception {
        var incomeUserDto = ExpectedDataFromDB.getExpectedUsersDtoFromDB().get(0);

        mockMvc.perform(patch(BASE_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeUserDto))
                )
                .andExpect(status().isOk());
    }

}