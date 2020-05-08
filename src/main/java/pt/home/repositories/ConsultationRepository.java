package pt.home.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.home.domain.Consultation;

import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    Optional<Consultation> findById(Long id);
}
