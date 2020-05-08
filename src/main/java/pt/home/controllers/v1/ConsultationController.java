package pt.home.controllers.v1;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.api.v1.model.ConsultationListDTO;
import pt.home.security.CurrentUser;
import pt.home.security.UserPrincipal;
import pt.home.services.ConsultationService;

import javax.validation.Valid;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@RestController
@RequestMapping(ConsultationController.BASE_URL)
public class ConsultationController {

    public static final String BASE_URL = "/api/v1/consultations";

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ConsultationListDTO getAllConsultations(@CurrentUser UserPrincipal currentUser) {
        return new ConsultationListDTO(consultationService.getAllConsultations(currentUser.getId()));
    }

    @GetMapping("/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultationListDTO getConsultationsByDate(@CurrentUser UserPrincipal currentUser, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyyMMdd") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyyMMdd") Date endDate) {

        return new ConsultationListDTO(
                consultationService.getConsultationsByDate(currentUser.getId(),
                        ZonedDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()),
                        ZonedDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault()))
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewConsultation(@Valid @RequestBody ConsultationDTO consultationDTO) {

        return new ResponseEntity(consultationService.createNewConsultation(consultationDTO),
                HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ConsultationDTO updateConsultation(@PathVariable Long id, @Valid @RequestBody ConsultationDTO consultationDTO) {
        return consultationService.saveConsultationByDTO(id, consultationDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultationById(id);
    }
}
