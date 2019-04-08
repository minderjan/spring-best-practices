package ch.zrhdev.spring.bestpractices.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    // ------ Endpoint: /customers --------------

    @GetMapping
    public ResponseEntity getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.CREATED);
    }

    // ------ Endpoint: /customers/{id} --------------

    @GetMapping(value = "/{id}")
    public ResponseEntity getCustomerById(@PathVariable( "id" ) Long customerId) {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateCustomerById(@RequestBody Customer customer, @PathVariable( "id" ) Long customerId) {
        return new ResponseEntity<>(customerService.updateCustomerById(customer, customerId), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCustomer(@PathVariable( "id" ) Long customerId) {
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
