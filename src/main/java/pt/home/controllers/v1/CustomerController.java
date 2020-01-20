package pt.home.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pt.home.api.v1.model.ConsultationListDTO;
import pt.home.api.v1.model.CustomerDTO;
import pt.home.api.v1.model.CustomerListDTO;
import pt.home.api.v1.model.PathologyListDTO;
import pt.home.services.CustomerService;

import java.util.ArrayList;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

    @GetMapping({"/{id}/consultations"})
    @ResponseStatus(HttpStatus.OK)
    public ConsultationListDTO getConsultationsByCustomerId(@PathVariable Long id) {
        return new ConsultationListDTO(new ArrayList<>(customerService.getCustomerById(id).getConsultations()));
    }

    @GetMapping({"/{id}/pathologies"})
    @ResponseStatus(HttpStatus.OK)
    public PathologyListDTO getPathologiesByCustomerId(@PathVariable Long id) {
        return new PathologyListDTO(new ArrayList<>(customerService.getCustomerById(id).getPathologies()));
    }

}
