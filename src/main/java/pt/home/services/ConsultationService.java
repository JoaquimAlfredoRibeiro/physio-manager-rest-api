package pt.home.services;

import pt.home.api.v1.model.ConsultationDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationService {

    List<ConsultationDTO> getAllConsultations();

    List<ConsultationDTO> getConsultationsByDate(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
