package pt.home.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.controllers.v1.ConsultationController;
import pt.home.services.ConsultationService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultationControllerTest {

    @Mock
    ConsultationService consultationService;

    @InjectMocks
    ConsultationController consultationController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(consultationController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllConsultations() throws Exception {

        //given
        ConsultationDTO consultationDTO1 = ConsultationDTO.builder().id(1L).location("Location 1").build();
        ConsultationDTO consultationDTO2 = ConsultationDTO.builder().id(1L).location("Location 2").build();
        List<ConsultationDTO> consultationDTOs = Arrays.asList(consultationDTO1, consultationDTO2);

        when(consultationService.getAllConsultations(any(Long.class))).thenReturn(consultationDTOs);

        //then
        ResultActions resultActions = mockMvc.perform(get(ConsultationController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consultations", hasSize(0)));

        resultActions.andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testGetConsultationsByDate() throws Exception {

        //given
        ConsultationDTO consultationDTO1 = ConsultationDTO.builder().id(1L).location("Location 1").build();
        ConsultationDTO consultationDTO2 = ConsultationDTO.builder().id(1L).location("Location 2").build();
        List<ConsultationDTO> consultationDTOs = Arrays.asList(consultationDTO1, consultationDTO2);

        when(consultationService.getConsultationsByDate(any(Long.class), any(ZonedDateTime.class), any(ZonedDateTime.class))).thenReturn(consultationDTOs);

        //then
        MvcResult result = (MvcResult) mockMvc.perform(get(ConsultationController.BASE_URL + "/20191111/20191112")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    public void testGetConsultationsByDateNoResults() throws Exception {

        //given
        List<ConsultationDTO> consultationDTOs = new ArrayList<>();

        when(consultationService.getConsultationsByDate(any(Long.class), any(ZonedDateTime.class), any(ZonedDateTime.class))).thenReturn(consultationDTOs);

        //then
        mockMvc.perform(get(ConsultationController.BASE_URL + "/20191111/20191112")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consultations", hasSize(0)));
    }
}
