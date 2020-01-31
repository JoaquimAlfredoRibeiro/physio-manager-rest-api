package pt.home.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    public Long id;

    private String fullName;
    private Long phoneNumber;
    private String email;
    private String address;

    @JsonIgnore
    private Set<PathologyDTO> pathologies;
    @JsonIgnore
    private Set<ConsultationDTO> consultations;

    @JsonProperty("patient_url")
    private String patientUrl;
}
