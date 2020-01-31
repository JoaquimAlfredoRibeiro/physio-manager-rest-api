package pt.home.services;

import org.springframework.stereotype.Service;
import pt.home.api.v1.mapper.PatientMapper;
import pt.home.api.v1.model.PatientDTO;
import pt.home.controllers.v1.PatientController;
import pt.home.domain.Patient;
import pt.home.repositories.PatientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> {
                    PatientDTO patientDTO = patientMapper.patientToPatientDTO(patient);
                    patientDTO.setPatientUrl(getPatientUrl(patient.getId()));
                    return patientDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::patientToPatientDTO)
                .map(patientDTO -> {
                    //set API URL
                    patientDTO.setPatientUrl(getPatientUrl(id));
                    return patientDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public PatientDTO createNewPatient(PatientDTO patientDTO) {
        return saveAndReturnDTO(patientMapper.patientDTOToPatient(patientDTO));
    }

    private PatientDTO saveAndReturnDTO(Patient patient) {
        Patient savedPatient = patientRepository.save(patient);

        PatientDTO returnDto = patientMapper.patientToPatientDTO(savedPatient);

        returnDto.setPatientUrl(getPatientUrl(savedPatient.getId()));

        return returnDto;
    }

    @Override
    public PatientDTO savePatientByDTO(Long id, PatientDTO patientDTO) {
        Patient patient = patientMapper.patientDTOToPatient(patientDTO);
        patient.setId(id);

        return saveAndReturnDTO(patient);
    }

    @Override
    public void deletePatientById(Long id) {
        patientRepository.deleteById(id);
    }

    private String getPatientUrl(Long id) {
        return PatientController.BASE_URL + "/" + id;
    }
}
