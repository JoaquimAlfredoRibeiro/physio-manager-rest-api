package pt.home.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.home.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
