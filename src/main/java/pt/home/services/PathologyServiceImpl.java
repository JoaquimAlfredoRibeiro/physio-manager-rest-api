package pt.home.services;

import org.springframework.stereotype.Service;
import pt.home.api.v1.mapper.PathologyMapper;
import pt.home.api.v1.model.PathologyDTO;
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
}
