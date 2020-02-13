package pt.home.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.home.api.v1.model.PathologyDTO;
import pt.home.api.v1.model.PathologyListDTO;
import pt.home.repositories.PathologyRepository;
import pt.home.services.PathologyService;

import javax.validation.Valid;

@RestController
@RequestMapping(PathologyController.BASE_URL)
public class PathologyController {

    public static final String BASE_URL = "/api/v1/pathologies";

    private final PathologyRepository pathologyRepository;

    private final PathologyService pathologyService;

    public PathologyController(PathologyRepository pathologyRepository, PathologyService pathologyService) {
        this.pathologyRepository = pathologyRepository;
        this.pathologyService = pathologyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PathologyListDTO getAllPathologies() {
        return new PathologyListDTO(pathologyService.getAllPathologies());
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public PathologyListDTO getPathologyByName(@PathVariable String name) {
        System.out.println(name);
        return new PathologyListDTO(pathologyService.findByNameIgnoreCaseContaining(name));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewPathology(@Valid @RequestBody PathologyDTO pathologyDTO) {

        return new ResponseEntity(pathologyService.createNewPathology(pathologyDTO),
                HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public PathologyDTO updatePatient(@PathVariable Long id, @Valid @RequestBody PathologyDTO pathologyDTO) {
        return pathologyService.savePathologyByDTO(id, pathologyDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deletePatient(@PathVariable Long id) {
        pathologyService.deletePathologyById(id);
    }
}
