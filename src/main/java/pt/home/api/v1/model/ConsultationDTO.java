package pt.home.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.home.domain.Patient;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDTO {

    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String notes;

    @JsonIgnore
    private Patient patient;

    private Long tempPatientId;

    private String title;
}

