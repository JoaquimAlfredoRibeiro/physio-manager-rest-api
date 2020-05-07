package pt.home.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.domain.Consultation;

@Mapper
public interface ConsultationMapper {

    ConsultationMapper INSTANCE = Mappers.getMapper(ConsultationMapper.class);

    ConsultationDTO consultationToConsultationDTO(Consultation consultation);

    Consultation consultationDTOToConsultation(ConsultationDTO consultationDTO);

    default ConsultationDTO consultationToConsultationDTOForAppointments(Consultation consultation) {
        if (consultation == null) {
            return null;
        }

        ConsultationDTO consultationDTO = new ConsultationDTO();

        consultationDTO.setId(consultation.getId());
        consultationDTO.setStartDate(consultation.getStartDate());
        consultationDTO.setEndDate(consultation.getEndDate());
        consultationDTO.setLocation(consultation.getLocation());
        consultationDTO.setNotes(consultation.getNotes());
        consultationDTO.setPatient(consultation.getPatient());
        consultationDTO.setTempPatientId(consultation.getTempPatientId());
        consultationDTO.setTitle(consultation.getPatient().getFullName());

        return consultationDTO;
    }
}
