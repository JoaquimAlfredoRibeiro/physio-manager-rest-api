package pt.home.mapper;

import org.junit.jupiter.api.Test;
import pt.home.api.v1.mapper.PathologyMapper;
import pt.home.api.v1.model.PathologyDTO;
import pt.home.domain.Pathology;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathologyMapperTest {

    //Constants
    public static final String NAME = "Pathology example";
    public static final String DESCRIPTION = "Description";

    PathologyMapper pathologyMapper = PathologyMapper.INSTANCE;

    @Test
    public void pathologyToPathologyDTO() {
        //given
        Pathology pathology = Pathology.builder().name(NAME).description(DESCRIPTION).build();

        //when
        PathologyDTO pathologyDTO = pathologyMapper.pathologyToPathologyDTO(pathology);

        //then
        assertEquals(NAME, pathologyDTO.getName());
        assertEquals(DESCRIPTION, pathologyDTO.getDescription());
    }

    @Test
    public void pathologyDTOToPathology() {
        //given
        PathologyDTO pathologyDTO = PathologyDTO.builder().name(NAME).description(DESCRIPTION).build();

        //when
        Pathology pathology = pathologyMapper.pathologyDTOToPathology(pathologyDTO);

        //then
        assertEquals(NAME, pathology.getName());
        assertEquals(DESCRIPTION, pathology.getDescription());
    }
}
