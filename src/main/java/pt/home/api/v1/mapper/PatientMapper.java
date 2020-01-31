package pt.home.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pt.home.api.v1.model.PatientDTO;
import pt.home.domain.Patient;

@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientDTO patientToPatientDTO(Patient patient);

    Patient patientDTOToPatient(PatientDTO patientDTO);
}
