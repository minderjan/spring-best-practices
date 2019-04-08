package ch.zrhdev.spring.bestpractices.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CustomerService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return this.saveOrUpdateCustomer(customer);
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("The customer with customer_id="
                + customerId + " could not be found in repository"));
    }

    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        LOGGER.debug("Return all {} customers from repository", allCustomers.size());
        return allCustomers;
    }

    public Customer updateCustomer(Customer customer) {
        LOGGER.debug("Customer with id customer_id={} will be updated", customer.getCustomerId());
        return this.saveOrUpdateCustomer(customer);
    }

    public Customer updateCustomerById(Customer customer, Long customerId) {
        // If you want to modify the single fields first
        Customer customerToUpdate = this.getCustomerById(customerId);
        customerToUpdate.setLastName(customer.getLastName());
        LOGGER.debug("Update lastName from customer_id={}", customerId);
        return this.saveOrUpdateCustomer(customerToUpdate);
    }

    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
        LOGGER.debug("customer with id customer_id={} deleted", customerId);
    }

    private Customer saveOrUpdateCustomer(Customer customer) {
        LOGGER.debug("Save new customer: {}", customer);
        return customerRepository.save(customer);
    }
}
