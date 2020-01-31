package pt.home.mapper;

import org.junit.jupiter.api.Test;
import pt.home.api.v1.mapper.ConsultationMapper;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.domain.Consultation;
import pt.home.domain.Patient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsultationMapperTest {

    //Constants
    public static final LocalDateTime DATE_TIME = LocalDateTime.of(2019, 11, 11, 10, 30);
    public static final String DESCRIPTION = "Description";

    ConsultationMapper consultationMapper = ConsultationMapper.INSTANCE;

    @Test
    public void consultationToConsultationDTO() {

        //given
        Consultation consultation = Consultation.builder().dateTime(DATE_TIME).description(DESCRIPTION).build();

        Patient patient = Patient.builder().id(1L).fullName("John Doe").build();
        consultation.setPatient(patient);

        //when
        ConsultationDTO consultationDTO = consultationMapper.consultationToConsultationDTO(consultation);

        //then
        assertEquals(DATE_TIME, consultationDTO.getDateTime());
        assertEquals(DESCRIPTION, consultationDTO.getDescription());
        assertEquals(patient, consultationDTO.getPatient());
    }

    @Test
    public void consultationDTOToConsultation() {

        //given
        ConsultationDTO consultationDTO = ConsultationDTO.builder().dateTime(DATE_TIME).description(DESCRIPTION).build();

        Patient patient = Patient.builder().id(1L).fullName("John Doe").build();
        consultationDTO.setPatient(patient);

        //when
        Consultation consultation = consultationMapper.consultationDTOToConsultation(consultationDTO);

        //then
        assertEquals(DATE_TIME, consultation.getDateTime());
        assertEquals(DESCRIPTION, consultation.getDescription());
        assertEquals(patient, consultation.getPatient());
    }
}
