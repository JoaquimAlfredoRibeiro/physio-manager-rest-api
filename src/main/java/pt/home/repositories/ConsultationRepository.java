package pt.home.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.home.domain.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
