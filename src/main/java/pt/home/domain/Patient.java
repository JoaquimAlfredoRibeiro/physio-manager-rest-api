package pt.home.domain;

import lombok.*;
import pt.home.domain.audit.UserDateAudit;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"pathologies", "consultations"})
public class Patient extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private Long phoneNumber;
    private String email;
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_pathologies", joinColumns = @JoinColumn(name = "patient_id"), inverseJoinColumns = @JoinColumn(name = "pathology_id"))
    private Set<Pathology> pathologies = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<Consultation> consultations = new HashSet<>();
}
