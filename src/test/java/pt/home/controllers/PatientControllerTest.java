package pt.home.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.api.v1.model.PathologyDTO;
import pt.home.api.v1.model.PatientDTO;
import pt.home.controllers.v1.PatientController;
import pt.home.repositories.PatientRepository;
import pt.home.services.PatientService;
import pt.home.services.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pt.home.controllers.AbstractRestControllerTest.asJsonString;

public class PatientControllerTest {

    @Mock
    PatientService patientService;

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientController patientController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(patientController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
//    @WithMockUser
    public void testGetAllPatients() throws Exception {

        //TODO - add testing with mock USER

        //given
        PatientDTO patient1 = PatientDTO.builder().id(1L).fullName("John Doe").patientUrl(PatientController.BASE_URL + "/1").build();
        PatientDTO patient2 = PatientDTO.builder().id(2L).fullName("Jane Buck").patientUrl(PatientController.BASE_URL + "/2").build();

        when(patientService.getAllPatients(any(Long.class))).thenReturn(Arrays.asList(patient1, patient2));

        mockMvc.perform(get(PatientController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.patients", hasSize(2)));
    }

    @Test
    public void testGetPatientById() throws Exception {

        //given
        PatientDTO patient1 = PatientDTO.builder().fullName("John Doe").patientUrl(PatientController.BASE_URL + "/1").build();

        when(patientService.getPatientById(anyLong())).thenReturn(patient1);

        //then
        mockMvc.perform(get(PatientController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", equalTo("John Doe")));
    }

    @Test
    public void testCreateNewPatient() throws Exception {
        //given
        PatientDTO patient = PatientDTO.builder().fullName("John Doe").email("email@email.com").phoneNumber("123456789").build();
        PatientDTO returnDTO = PatientDTO.builder().fullName(patient.getFullName()).email("email@email.com").phoneNumber("123456789").patientUrl(PatientController.BASE_URL + "/1").build();

        when(patientService.createNewPatient(patient)).thenReturn(returnDTO);

        //then
        mockMvc.perform(post(PatientController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", equalTo("John Doe")))
                .andExpect(jsonPath("$.patient_url", equalTo(PatientController.BASE_URL + "/1")));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        //given
        PatientDTO patient = PatientDTO.builder().fullName("John Doe").email("email@email.com").phoneNumber("123456789").build();
        PatientDTO returnDTO = PatientDTO.builder().fullName(patient.getFullName()).patientUrl(PatientController.BASE_URL + "/1").build();

        when(patientService.savePatientByDTO(anyLong(), any(PatientDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(PatientController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", equalTo("John Doe")))
                .andExpect(jsonPath("$.patient_url", equalTo(PatientController.BASE_URL + "/1")));
    }

    @Test
    public void testDeletePatient() throws Exception {

        mockMvc.perform(delete(PatientController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(patientService).deletePatientById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(patientService.getPatientById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(PatientController.BASE_URL + "/111")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetConsultationsByPatientId() throws Exception {

        Set<ConsultationDTO> consultation = new HashSet<>();
        consultation.add(ConsultationDTO.builder()
                .id(1L)
                .startDate(LocalDateTime.of(2019, 11, 10, 10, 30))
                .location("Diagnosis Consultation")
                .build());
        consultation.add(ConsultationDTO.builder()
                .id(2L)
                .startDate(LocalDateTime.of(2019, 11, 11, 11, 30))
                .location("Follow-up Consultation")
                .build());

        //given
        PatientDTO patient1 = PatientDTO.builder()
                .fullName("John Doe")
                .patientUrl(PatientController.BASE_URL + "/1")
                .consultations(consultation).build();

        when(patientService.getPatientById(anyLong())).thenReturn(patient1);

        //then
        mockMvc.perform(get(PatientController.BASE_URL + "/1/consultations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consultations", hasSize(2)));
    }

    @Test
    public void testGetPathologiesByPatientId() throws Exception {

        Set<PathologyDTO> pathologyDTOs = new HashSet<>();
        pathologyDTOs.add(PathologyDTO.builder()
                .id(1L)
                .name("Broken Arm")
                .description("Diagnosis Consultation")
                .build());
        pathologyDTOs.add(PathologyDTO.builder()
                .id(2L)
                .name("Broken Leg")
                .description("Follow-up Consultation")
                .build());

        //given
        PatientDTO patient1 = PatientDTO.builder()
                .fullName("John Doe")
                .patientUrl(PatientController.BASE_URL + "/1")
                .pathologies(pathologyDTOs).build();

        when(patientService.getPatientById(anyLong())).thenReturn(patient1);

        //then
        mockMvc.perform(get(PatientController.BASE_URL + "/1/pathologies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pathologies", hasSize(2)));
    }
}
