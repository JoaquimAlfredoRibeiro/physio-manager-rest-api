package pt.home.mapper;

import org.junit.jupiter.api.Test;
import pt.home.api.v1.mapper.ConsultationMapper;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.domain.Consultation;
import pt.home.domain.Patient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsultationMapperTest {

    //Constants
    public static final String LOCATION = "Location";
    public static final LocalDateTime DATE_TIME_1 = LocalDateTime.of(2019, 11, 11, 10, 30);
    public static final ZonedDateTime LOCAL_DATE_TIME_1 = DATE_TIME_1.atZone(ZoneId.of("Europe/Lisbon"));
    ;

    ConsultationMapper consultationMapper = ConsultationMapper.INSTANCE;

    @Test
    public void consultationToConsultationDTO() {

        //given
        Consultation consultation = Consultation.builder().startDate(LOCAL_DATE_TIME_1).location(LOCATION).build();

        Patient patient = Patient.builder().id(1L).fullName("John Doe").build();
        consultation.setPatient(patient);

        //when
        ConsultationDTO consultationDTO = consultationMapper.consultationToConsultationDTO(consultation);

        //then
        assertEquals(LOCAL_DATE_TIME_1, consultationDTO.getStartDate());
        assertEquals(LOCATION, consultationDTO.getLocation());
        assertEquals(patient, consultationDTO.getPatient());
    }

    @Test
    public void consultationDTOToConsultation() {

        //given
        ConsultationDTO consultationDTO = ConsultationDTO.builder().startDate(LOCAL_DATE_TIME_1).location(LOCATION).build();

        Patient patient = Patient.builder().id(1L).fullName("John Doe").build();
        consultationDTO.setPatient(patient);

        //when
        Consultation consultation = consultationMapper.consultationDTOToConsultation(consultationDTO);

        //then
        assertEquals(LOCAL_DATE_TIME_1, consultation.getStartDate());
        assertEquals(LOCATION, consultation.getLocation());
        assertEquals(patient, consultation.getPatient());
    }
}
