package pt.home.services;

import pt.home.api.v1.model.ConsultationDTO;

import java.time.ZonedDateTime;
import java.util.List;

public interface ConsultationService {

    List<ConsultationDTO> getAllConsultations(Long id);

    List<ConsultationDTO> getConsultationsByDate(Long id, ZonedDateTime startDateTime, ZonedDateTime endDateTime);

    ConsultationDTO createNewConsultation(ConsultationDTO consultationDTO);

    ConsultationDTO saveConsultationByDTO(Long id, ConsultationDTO consultationDTO);

    void deleteConsultationById(Long id);
}
