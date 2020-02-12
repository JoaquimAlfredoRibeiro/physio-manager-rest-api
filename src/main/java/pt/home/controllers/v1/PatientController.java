package pt.home.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.home.api.v1.model.ConsultationListDTO;
import pt.home.api.v1.model.PathologyListDTO;
import pt.home.api.v1.model.PatientDTO;
import pt.home.api.v1.model.PatientListDTO;
import pt.home.payload.ApiResponse;
import pt.home.repositories.PatientRepository;
import pt.home.security.CurrentUser;
import pt.home.security.UserPrincipal;
import pt.home.services.PatientService;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(PatientController.BASE_URL)
//@CrossOrigin(origins = "*")
public class PatientController {

    public static final String BASE_URL = "/api/v1/patients";

    private final PatientRepository patientRepository;

    private final PatientService patientService;

    public PatientController(PatientRepository patientRepository, PatientService patientService) {
        this.patientRepository = patientRepository;
        this.patientService = patientService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PatientListDTO getAllPatients(@CurrentUser UserPrincipal currentUser) {
        return new PatientListDTO(patientService.getAllPatients(currentUser.getId()));
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public PatientDTO getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewPatient(@Valid @RequestBody PatientDTO patientDTO) {

        if (patientRepository.existsByEmail(patientDTO.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use."),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(patientService.createNewPatient(patientDTO),
                HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public PatientDTO updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO patientDTO) {
        return patientService.savePatientByDTO(id, patientDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatientById(id);
    }

    @GetMapping({"/{id}/consultations"})
    @ResponseStatus(HttpStatus.OK)
    public ConsultationListDTO getConsultationsByPatientId(@PathVariable Long id) {
        return new ConsultationListDTO(new ArrayList<>(patientService.getPatientById(id).getConsultations()));
    }

    @GetMapping({"/{id}/pathologies"})
    @ResponseStatus(HttpStatus.OK)
    public PathologyListDTO getPathologiesByPatientId(@PathVariable Long id) {
        return new PathologyListDTO(new ArrayList<>(patientService.getPatientById(id).getPathologies()));
    }

}
