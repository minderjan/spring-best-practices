package ch.zrhdev.spring.bestpractices.customer;

import ch.zrhdev.spring.bestpractices.customer.Customer;
import ch.zrhdev.spring.bestpractices.customer.CustomerRepository;
import ch.zrhdev.spring.bestpractices.customer.CustomerService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service Unit Tests")
public class CustomerServiceTests {

    CustomerRepository customerRepository = mock(CustomerRepository.class);
    CustomerService customerService = new CustomerService();

    private Customer jan = new Customer();

    @BeforeEach
    void setMockOutput() {
        when(customerRepository.findById(jan.getCustomerId()).get()).thenReturn(jan);
    }


    @Test
    public void whenValidId_thenCustomerShouldBeFound() {

        long customerId = 1;
        //System.out.println(customerRepository.getOne(longValue(1)));
        Customer found = customerService.getCustomerById(customerId);

        assertThat(found.getCustomerId())
                .isEqualTo(customerId);
    }
}
