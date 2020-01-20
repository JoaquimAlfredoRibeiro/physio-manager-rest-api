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
}
