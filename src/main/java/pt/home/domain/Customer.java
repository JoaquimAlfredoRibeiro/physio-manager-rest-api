package pt.home.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"pathologies", "consultations"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private Long phoneNumber;
    private String email;
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_pathologies", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "pathology_id"))
    private Set<Pathology> pathologies = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<Consultation> consultations = new HashSet<>();
}
