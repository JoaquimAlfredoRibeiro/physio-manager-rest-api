package pt.home.controllers.v1;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pt.home.api.v1.model.ConsultationListDTO;
import pt.home.services.ConsultationService;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public ConsultationListDTO getAllConsultations() {
        return new ConsultationListDTO(consultationService.getAllConsultations());
    }

    @GetMapping("/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultationListDTO getConsultationsByDate(@PathVariable("startDate") @DateTimeFormat(pattern = "yyyyMMdd") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyyMMdd") Date endDate) {

        return new ConsultationListDTO(
                consultationService.getConsultationsByDate(LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()),
                        LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault())));
    }


}
