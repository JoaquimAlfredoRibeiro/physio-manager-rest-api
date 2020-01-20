package pt.home.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.home.domain.Pathology;

import java.util.List;

public interface PathologyRepository extends JpaRepository<Pathology, Long> {

    List<Pathology> findByNameIgnoreCaseContaining(String name);
}
