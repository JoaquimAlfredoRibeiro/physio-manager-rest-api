package pt.home.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.home.api.v1.model.ConsultationDTO;
import pt.home.api.v1.model.CustomerDTO;
import pt.home.api.v1.model.PathologyDTO;
import pt.home.controllers.v1.CustomerController;
import pt.home.services.CustomerService;
import pt.home.services.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pt.home.controllers.AbstractRestControllerTest.asJsonString;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {

        //given
        CustomerDTO customer1 = CustomerDTO.builder().fullName("John Doe").customerUrl(CustomerController.BASE_URL + "/1").build();
        CustomerDTO customer2 = CustomerDTO.builder().fullName("Jane Buck").customerUrl(CustomerController.BASE_URL + "/2").build();

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {

        //given
        CustomerDTO customer1 = CustomerDTO.builder().fullName("John Doe").customerUrl(CustomerController.BASE_URL + "/1").build();

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        //then
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", equalTo("John Doe")));
    }

    @Test
    public void testCreateNewCustomer() throws Exception {
        //given
        CustomerDTO customer = CustomerDTO.builder().fullName("John Doe").build();
        CustomerDTO returnDTO = CustomerDTO.builder().fullName(customer.getFullName()).customerUrl(CustomerController.BASE_URL + "/1").build();

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        //then
        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", equalTo("John Doe")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO customer = CustomerDTO.builder().fullName("John Doe").build();
        CustomerDTO returnDTO = CustomerDTO.builder().fullName(customer.getFullName()).customerUrl(CustomerController.BASE_URL + "/1").build();

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", equalTo("John Doe")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/111")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetConsultationsByCustomerId() throws Exception {

        Set<ConsultationDTO> consultation = new HashSet<>();
        consultation.add(ConsultationDTO.builder()
                .id(1L)
                .dateTime(LocalDateTime.of(2019, 11, 10, 10, 30))
                .description("Diagnosis Consultation")
                .build());
        consultation.add(ConsultationDTO.builder()
                .id(2L)
                .dateTime(LocalDateTime.of(2019, 11, 11, 11, 30))
                .description("Follow-up Consultation")
                .build());

        //given
        CustomerDTO customer1 = CustomerDTO.builder()
                .fullName("John Doe")
                .customerUrl(CustomerController.BASE_URL + "/1")
                .consultations(consultation).build();

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        //then
        mockMvc.perform(get(CustomerController.BASE_URL + "/1/consultations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consultations", hasSize(2)));
    }

    @Test
    public void testGetPathologiesByCustomerId() throws Exception {

        Set<PathologyDTO> pathologyDTOs = new HashSet<>();
        pathologyDTOs.add(PathologyDTO.builder()
                .id(1L)
                .name("Broken Arm")
                .description("Diagnosis Consultation")
                .build());
        pathologyDTOs.add(PathologyDTO.builder()
                .id(2L)
                .name("Broken Leg")
                .description("Follow-up Consultation")
                .build());

        //given
        CustomerDTO customer1 = CustomerDTO.builder()
                .fullName("John Doe")
                .customerUrl(CustomerController.BASE_URL + "/1")
                .pathologies(pathologyDTOs).build();

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        //then
        mockMvc.perform(get(CustomerController.BASE_URL + "/1/pathologies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pathologies", hasSize(2)));
    }
}
