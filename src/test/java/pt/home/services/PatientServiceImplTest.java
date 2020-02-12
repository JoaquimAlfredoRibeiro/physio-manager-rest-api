package pt.home.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.home.api.v1.mapper.PatientMapper;
import pt.home.api.v1.model.PatientDTO;
import pt.home.domain.Patient;
import pt.home.repositories.PatientRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PatientServiceImplTest {

    public static final String FULL_NAME_1 = "John the Doest";
    public static final String FULL_NAME_2 = "Jane the Not so Doe";

    @Mock
    PatientRepository patientRepository;

    PatientMapper patientMapper = PatientMapper.INSTANCE;

    PatientService patientService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        patientService = new PatientServiceImpl(patientRepository, patientMapper);
    }

    @Test
    public void getAllPatients() throws Exception {
        //given
        List<Patient> patients = Arrays.asList(new Patient(), new Patient(), new Patient());

        when(patientRepository.findAll()).thenReturn(patients);

        //when
        List<PatientDTO> patientDTOS = patientService.getAllPatients(1L);

        //then
        assertEquals(3, patientDTOS.size());
    }


    @Test
    public void getPatientById() throws Exception {
        //given
        Patient patient = Patient.builder().id(1L).fullName(FULL_NAME_1).build();

        when(patientRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(patient));

        //when
        PatientDTO patientDTO = patientService.getPatientById(1L);

        //then
        assertEquals(FULL_NAME_1, patientDTO.getFullName());
    }

    @Test
    public void createNewPatient() throws Exception {

        //given
        PatientDTO patientDTO = PatientDTO.builder().fullName(FULL_NAME_1).build();

        Patient savedPatient = Patient.builder().id(1L).fullName(FULL_NAME_1).build();

        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        //when
        PatientDTO savedDto = patientService.createNewPatient(patientDTO);

        //then
        assertEquals(patientDTO.getFullName(), savedDto.getFullName());
        assertEquals("/api/v1/patients/1", savedDto.getPatientUrl());
    }

    @Test
    public void savePatientByDTO() throws Exception {

        //given
        PatientDTO patientDTO = PatientDTO.builder().fullName(FULL_NAME_1).build();

        Patient savedPatient = Patient.builder().id(1L).fullName(FULL_NAME_1).build();

        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        //when
        PatientDTO savedDto = patientService.savePatientByDTO(1L, patientDTO);

        //then
        assertEquals(patientDTO.getFullName(), savedDto.getFullName());
        assertEquals("/api/v1/patients/1", savedDto.getPatientUrl());
    }

    @Test
    public void deletePatientById() throws Exception {

        Long id = 1L;

        patientRepository.deleteById(id);

        verify(patientRepository, times(1)).deleteById(anyLong());
    }

}
