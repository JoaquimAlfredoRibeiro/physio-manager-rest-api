package pt.home.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.home.api.v1.mapper.ConsultationMapper;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.domain.Consultation;
import pt.home.repositories.ConsultationRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConsultationServiceImplTest {

    //Constants
    public static final LocalDateTime DATE_TIME_1 = LocalDateTime.of(2019, 11, 11, 10, 30);
    public static final LocalDateTime DATE_TIME_2 = LocalDateTime.of(2019, 11, 12, 10, 30);
    public static final LocalDateTime DATE_TIME_3 = LocalDateTime.of(2019, 11, 13, 10, 30);

    ConsultationService consultationService;

    @Mock
    ConsultationRepository consultationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        consultationService = new ConsultationServiceImpl(consultationRepository, ConsultationMapper.INSTANCE);
    }

    @Test
    public void getAllConsultations() {
        //given
        List<Consultation> consultations = Arrays.asList(new Consultation(), new Consultation(), new Consultation());
        when(consultationRepository.findAll()).thenReturn(consultations);

        //when
        List<ConsultationDTO> consultationDTOS = consultationService.getAllConsultations();

        //then
        assertEquals(3, consultationDTOS.size());
    }

    @Test
    public void getConsultationsByDate() {
        //given
        Consultation consultation1 = Consultation.builder().dateTime(DATE_TIME_1).build();
        Consultation consultation2 = Consultation.builder().dateTime(DATE_TIME_2).build();
        Consultation consultation3 = Consultation.builder().dateTime(DATE_TIME_3).build();

        List<Consultation> consultations = Arrays.asList(consultation1, consultation2, consultation3);
        when(consultationRepository.findAll()).thenReturn(consultations);

        //when
        List<ConsultationDTO> consultationDTOS = consultationService.
                getConsultationsByDate(LocalDateTime.of(2019, 11, 12, 0, 0),
                        LocalDateTime.of(2019, 11, 12, 23, 59));

        //then
        assertEquals(1, consultationDTOS.size());
        assertEquals(DATE_TIME_2, consultationDTOS.get(0).getDateTime());
    }
}
