package pt.home.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pt.home.api.v1.model.PathologyDTO;
import pt.home.domain.Pathology;

@Mapper
public interface PathologyMapper {

    PathologyMapper INSTANCE = Mappers.getMapper(PathologyMapper.class);

    PathologyDTO pathologyToPathologyDTO(Pathology patient);

    Pathology pathologyDTOToPathology(PathologyDTO consultationDTO);
}
