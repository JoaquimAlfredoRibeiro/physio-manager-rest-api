package pt.home.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PathologyDTO {
    private Long id;

    @NotBlank
    @Size(min = 6, max = 120)
    private String name;

    private String description;

    @JsonProperty("pathology_url")
    private String pathologyUrl;
}
