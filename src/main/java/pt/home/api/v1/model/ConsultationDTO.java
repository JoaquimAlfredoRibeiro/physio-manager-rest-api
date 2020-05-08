package pt.home.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.home.domain.Patient;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDTO {

    private Long id;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    private String location;
    private String notes;

    @JsonIgnore
    private Patient patient;

    private Long tempPatientId;
    private Boolean active;

    private String title;
}

