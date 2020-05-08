package pt.home.services;

import org.springframework.stereotype.Service;
import pt.home.api.v1.mapper.ConsultationMapper;
import pt.home.api.v1.mapper.PatientMapper;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.domain.Consultation;
import pt.home.domain.Patient;
import pt.home.repositories.ConsultationRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final PatientService patientService;
    private final PatientMapper patientMapper;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository, ConsultationMapper consultationMapper, PatientService patientService, PatientMapper patientMapper) {
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @Override
    public List<ConsultationDTO> getAllConsultations(Long id) {
        return consultationRepository.findAll()
                .stream()
                .filter(patient -> patient.getCreatedBy().equals(id))
                .map(consultationMapper::consultationToConsultationDTOForAppointments)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultationDTO> getConsultationsByDate(Long id, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        return this.getAllConsultations(id)
                .stream()
                .filter(consultationDTO -> consultationDTO.getStartDate().compareTo(startDateTime) > 0)
                .filter(consultationDTO -> consultationDTO.getEndDate().compareTo(endDateTime) < 0)
                .collect(Collectors.toList());
    }


    @Override
    public ConsultationDTO createNewConsultation(ConsultationDTO consultationDTO) {
        return saveAndReturnDTO(consultationMapper.consultationDTOToConsultation(consultationDTO));
    }

    private ConsultationDTO saveAndReturnDTO(Consultation consultation) {

        Patient patient = patientMapper.patientDTOToPatient(patientService.getPatientById(consultation.getTempPatientId()));

        consultation.setPatient(patient);

        Consultation savedConsultation = consultationRepository.save(consultation);

        ConsultationDTO returnDto = consultationMapper.consultationToConsultationDTO(savedConsultation);

        return returnDto;
    }

    @Override
    public ConsultationDTO saveConsultationByDTO(Long id, ConsultationDTO consultationDTO) {
        Consultation consultation = consultationMapper.consultationDTOToConsultation(consultationDTO);
        consultation.setId(id);

        return saveAndReturnDTO(consultation);
    }

    @Override
    public void deleteConsultationById(Long id) {
        consultationRepository.deleteById(id);
    }
}
