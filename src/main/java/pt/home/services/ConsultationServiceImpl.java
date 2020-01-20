package pt.home.services;

import org.springframework.stereotype.Service;
import pt.home.api.v1.mapper.ConsultationMapper;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.repositories.ConsultationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository, ConsultationMapper consultationMapper) {
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
    }

    @Override
    public List<ConsultationDTO> getAllConsultations() {
        return consultationRepository.findAll()
                .stream()
                .map(consultationMapper::consultationToConsultationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultationDTO> getConsultationsByDate(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return this.getAllConsultations()
                .stream()
                .filter(consultationDTO -> consultationDTO.getDateTime().compareTo(startDateTime) > 0)
                .filter(consultationDTO -> consultationDTO.getDateTime().compareTo(endDateTime) < 0)
                .collect(Collectors.toList());
    }
}
