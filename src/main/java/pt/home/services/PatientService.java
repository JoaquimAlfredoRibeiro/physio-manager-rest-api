package pt.home.services;

import pt.home.api.v1.model.PatientDTO;

import java.util.List;

public interface PatientService {

    List<PatientDTO> getAllPatients(Long id);

    PatientDTO getPatientById(Long id);

    PatientDTO createNewPatient(PatientDTO patientDTO);

    PatientDTO savePatientByDTO(Long id, PatientDTO patientDTO);

    void deletePatientById(Long id);
}
