//package ru.javlasov.sportsplanner.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.javlasov.sportsplanner.ExpectedDataFromDB;
//import ru.javlasov.sportsplanner.controller.rest.TrainRestController;
//import ru.javlasov.sportsplanner.dto.TrainDto;
//import ru.javlasov.sportsplanner.security.SecurityConfig;
//import ru.javlasov.sportsplanner.service.TrainService;
//import ru.javlasov.sportsplanner.service.UserCredentialsService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(TrainRestController.class)
//@Import({SecurityConfig.class})
//class TrainRestControllerTest {
//
//    private final static String BASE_URL = "/api/v1/trains";
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private TrainService mockTrainService;
//
//    @MockBean
//    private UserCredentialsService mockUserCredentialsService;
//
//    @Test
//    @DisplayName("Should get all trains and get OK status")
//    @WithMockUser
//    void getAllTrainsTest() throws Exception {
//        Set<TrainDto> expectedTrainsDtoSet = ExpectedDataFromDB.getExpectedTrainDtoFromDB();
//        List<TrainDto> expectedTrainsDtoList = new ArrayList<>(expectedTrainsDtoSet);
//        given(mockTrainService.getAllTrainsCurrentUser()).willReturn(expectedTrainsDtoList);
//        mockMvc.perform(get(BASE_URL)
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper
//                        .writeValueAsString(ExpectedDataFromDB.getExpectedTrainDtoFromDB())));
//    }
//
//    @Test
//    @WithMockUser
//    @DisplayName("Should delete train and get OK status")
//    void deleteTrainByIdTest() throws Exception {
//        // given
//        var expectedDeletedId = 1L;
//
//        // when
//        mockMvc.perform(delete(BASE_URL + "/" + expectedDeletedId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser
//    @DisplayName("Should create train and get CREATED status")
//    void createTrainTest() throws Exception {
//        var incomeHealthDto = ExpectedDataFromDB.getExpectedTrainDtoFromDB().iterator().next();
//        incomeHealthDto.setId(null);
//
//        mockMvc.perform(post(BASE_URL)
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(incomeHealthDto))
//                )
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    @WithMockUser
//    @DisplayName("Should edit train and get OK status")
//    void editTrainTest() throws Exception {
//        var incomeHealthDto = ExpectedDataFromDB.getExpectedTrainDtoFromDB().iterator().next();
//
//        mockMvc.perform(patch(BASE_URL)
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(incomeHealthDto))
//                )
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("Should get 302 (redirect) status if created with not auth user")
//    void createTrainWithNotAuthUserTest() throws Exception {
//        mockMvc.perform(post(BASE_URL)
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is3xxRedirection());
//    }
//
//}