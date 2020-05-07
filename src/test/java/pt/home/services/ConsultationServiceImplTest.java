package pt.home.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.home.api.v1.mapper.ConsultationMapper;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.domain.Consultation;
import pt.home.domain.Patient;
import pt.home.repositories.ConsultationRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConsultationServiceImplTest {

    //Constants
    public static final LocalDateTime DATE_TIME_1 = LocalDateTime.of(2019, 11, 11, 10, 30);
    public static final LocalDateTime DATE_TIME_2 = LocalDateTime.of(2019, 11, 11, 11, 30);
    public static final LocalDateTime DATE_TIME_3 = LocalDateTime.of(2019, 11, 12, 10, 30);
    public static final LocalDateTime DATE_TIME_4 = LocalDateTime.of(2019, 11, 12, 11, 30);

    ConsultationService consultationService;

    @Mock
    ConsultationRepository consultationRepository;


    Consultation c1 = new Consultation();
    Consultation c2 = new Consultation();
    Consultation c3 = new Consultation();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        consultationService = new ConsultationServiceImpl(consultationRepository, ConsultationMapper.INSTANCE, null, null);

        Patient patient = Patient.builder().fullName("Full Name").build();

        c1.setCreatedBy(1L);
        c1.setPatient(patient);
        c2.setCreatedBy(1L);
        c2.setPatient(patient);
        c3.setCreatedBy(1L);
        c3.setPatient(patient);
    }

    @Test
    public void getAllConsultations() {
        //given
        List<Consultation> consultations = Arrays.asList(c1, c2, c3);
        when(consultationRepository.findAll()).thenReturn(consultations);

        //when
        List<ConsultationDTO> consultationDTOS = consultationService.getAllConsultations(1L);

        //then
        assertEquals(3, consultationDTOS.size());
    }

    @Test
    public void getConsultationsByDate() {
        //given
        Patient patient = Patient.builder().fullName("Full Name").build();
        Consultation consultation1 = Consultation.builder().startDate(DATE_TIME_1).endDate(DATE_TIME_2).patient(patient).build();
        Consultation consultation2 = Consultation.builder().startDate(DATE_TIME_3).endDate(DATE_TIME_4).patient(patient).build();

        consultation1.setCreatedBy(1L);
        consultation2.setCreatedBy(1L);

        List<Consultation> consultations = Arrays.asList(consultation1, consultation2);
        when(consultationRepository.findAll()).thenReturn(consultations);

        //when
        List<ConsultationDTO> consultationDTOS = consultationService.
                getConsultationsByDate(1L, LocalDateTime.of(2019, 11, 12, 0, 0),
                        LocalDateTime.of(2019, 11, 12, 23, 59));

        //then
        assertEquals(1, consultationDTOS.size());
        assertEquals(DATE_TIME_3, consultationDTOS.get(0).getStartDate());
    }
}
