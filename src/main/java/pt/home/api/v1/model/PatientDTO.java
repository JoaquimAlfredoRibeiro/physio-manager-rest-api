package pt.home.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    public Long id;

    @NotBlank
    @Size(min = 6, max = 120)
    private String fullName;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    private String address;

    @JsonIgnore
    private Set<PathologyDTO> pathologies;
    @JsonIgnore
    private Set<ConsultationDTO> consultations;

    @JsonProperty("patient_url")
    private String patientUrl;
}
