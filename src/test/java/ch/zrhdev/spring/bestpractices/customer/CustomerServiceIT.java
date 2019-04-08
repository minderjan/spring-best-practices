package ch.zrhdev.spring.bestpractices.customer;

import ch.zrhdev.spring.bestpractices.customer.Customer;
import ch.zrhdev.spring.bestpractices.customer.CustomerRepository;
import ch.zrhdev.spring.bestpractices.customer.CustomerService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.aspectj.runtime.internal.Conversions.longValue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@DisplayName("Customer Service Tests")
public class CustomerServiceIT {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        Customer jan = new Customer("Jan", "Minder");
        jan.setCustomerId(longValue(1));
        customerRepository.save(jan);
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
