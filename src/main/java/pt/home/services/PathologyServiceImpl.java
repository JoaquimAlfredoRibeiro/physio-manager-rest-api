package pt.home.services;

import org.springframework.stereotype.Service;
import pt.home.api.v1.mapper.PathologyMapper;
import pt.home.api.v1.model.PathologyDTO;
import pt.home.controllers.v1.PatientController;
import pt.home.domain.Pathology;
import pt.home.repositories.PathologyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathologyServiceImpl implements PathologyService {

    private final PathologyRepository pathologyRepository;
    private final PathologyMapper pathologyMapper;

    public PathologyServiceImpl(PathologyRepository pathologyRepository, PathologyMapper pathologyMapper) {
        this.pathologyRepository = pathologyRepository;
        this.pathologyMapper = pathologyMapper;
    }

    @Override
    public List<PathologyDTO> getAllPathologies() {
        return pathologyRepository.findAll()
                .stream()
                .map(pathologyMapper::pathologyToPathologyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PathologyDTO> findByNameIgnoreCaseContaining(String name) {
        return pathologyRepository.findByNameIgnoreCaseContaining(name)
                .stream()
                .map(pathologyMapper::pathologyToPathologyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PathologyDTO createNewPathology(PathologyDTO pathologyDTO) {
        return saveAndReturnDTO(pathologyMapper.pathologyDTOToPathology(pathologyDTO));
    }

    private PathologyDTO saveAndReturnDTO(Pathology pathology) {
        Pathology savedPathology = pathologyRepository.save(pathology);

        PathologyDTO returnDto = pathologyMapper.pathologyToPathologyDTO(savedPathology);

        returnDto.setPathologyUrl(getPathologyUrl(savedPathology.getId()));

        return returnDto;
    }

    @Override
    public PathologyDTO savePathologyByDTO(Long id, PathologyDTO pathologyDTO) {
        Pathology pathology = pathologyMapper.pathologyDTOToPathology(pathologyDTO);
        pathology.setId(id);

        return saveAndReturnDTO(pathology);
    }

    @Override
    public void deletePathologyById(Long id) {
        pathologyRepository.deleteById(id);
    }

    private String getPathologyUrl(Long id) {
        return PatientController.BASE_URL + "/" + id;
    }
}
