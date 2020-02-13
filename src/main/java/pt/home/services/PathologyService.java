package pt.home.services;

import pt.home.api.v1.model.PathologyDTO;

import java.util.List;

public interface PathologyService {

    List<PathologyDTO> getAllPathologies();

    List<PathologyDTO> findByNameIgnoreCaseContaining(String name);

    PathologyDTO createNewPathology(PathologyDTO pathologyDTO);

    PathologyDTO savePathologyByDTO(Long id, PathologyDTO pathologyDTO);

    void deletePathologyById(Long id);
}
