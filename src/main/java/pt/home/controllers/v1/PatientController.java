package pt.home.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pt.home.api.v1.model.ConsultationListDTO;
import pt.home.api.v1.model.PathologyListDTO;
import pt.home.api.v1.model.PatientDTO;
import pt.home.api.v1.model.PatientListDTO;
import pt.home.services.PatientService;

import java.util.ArrayList;

@RestController
@RequestMapping(PatientController.BASE_URL)
@CrossOrigin(origins = "*")
public class PatientController {

    public static final String BASE_URL = "/api/v1/patients";

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PatientListDTO getAllPatients() {
        return new PatientListDTO(patientService.getAllPatients());
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public PatientDTO getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO createNewPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.createNewPatient(patientDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public PatientDTO updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
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
