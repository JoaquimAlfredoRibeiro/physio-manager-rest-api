package pt.home.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.home.api.v1.mapper.PathologyMapper;
import pt.home.api.v1.model.PathologyDTO;
import pt.home.domain.Pathology;
import pt.home.repositories.PathologyRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PathologyServiceImplTest {

    public static final Long ID = 1L;
    public static final String NAME = "Pathology name";
    public static final String DESCRIPTION = "Description";

    PathologyService pathologyService;

    @Mock
    PathologyRepository pathologyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        pathologyService = new PathologyServiceImpl(pathologyRepository, PathologyMapper.INSTANCE);
    }

    @Test
    public void getAllPathologies() {
        //given
        List<Pathology> pathologies = Arrays.asList(new Pathology(), new Pathology(), new Pathology());
        when(pathologyRepository.findAll()).thenReturn(pathologies);

        //when
        List<PathologyDTO> pathologyDTOS = pathologyService.getAllPathologies();

        //then
        assertEquals(3, pathologyDTOS.size());
    }

    @Test
    public void getSinglePathologyByName() {
        //given
        List<Pathology> pathology = new ArrayList<>();
        pathology.add(Pathology.builder().id(ID).name(NAME).description(DESCRIPTION).build());
        when(pathologyRepository.findByNameIgnoreCaseContaining(anyString())).thenReturn(pathology);

        //when
        List<PathologyDTO> pathologyListDTO = pathologyService.findByNameIgnoreCaseContaining(NAME);

        //then
        assertEquals(1, pathologyListDTO.size());
        assertEquals(ID, pathologyListDTO.get(0).getId());
        assertEquals(NAME, pathologyListDTO.get(0).getName());
        assertEquals(DESCRIPTION, pathologyListDTO.get(0).getDescription());
    }

    @Test
    public void getMultiplePathologyByName() {
        //given
        List<Pathology> pathology = new ArrayList<>();
        pathology.add(Pathology.builder().id(ID).name(NAME).description(DESCRIPTION).build());
        pathology.add(Pathology.builder().id(ID).name(NAME).description(DESCRIPTION).build());
        pathology.add(Pathology.builder().id(ID).name(NAME).description(DESCRIPTION).build());
        when(pathologyRepository.findByNameIgnoreCaseContaining(anyString())).thenReturn(pathology);

        //when
        List<PathologyDTO> pathologyListDTO = pathologyService.findByNameIgnoreCaseContaining(NAME);

        //then
        assertEquals(3, pathologyListDTO.size());
    }
}
