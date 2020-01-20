package pt.home.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pt.home.api.v1.model.PathologyListDTO;
import pt.home.services.PathologyService;

@RestController
@RequestMapping(PathologyController.BASE_URL)
public class PathologyController {

    public static final String BASE_URL = "/api/v1/pathologies";

    private final PathologyService pathologyService;

    public PathologyController(PathologyService pathologyService) {
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
}
