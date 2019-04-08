package ch.zrhdev.spring.bestpractices.customer;

import ch.zrhdev.spring.bestpractices.customer.Customer;
import ch.zrhdev.spring.bestpractices.customer.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindById_theReturnCustomer() {
        // given
        Customer alex = new Customer("alex", "alexis");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        Customer found = customerRepository.findById(alex.getCustomerId()).get();

        // then
        assertThat(found.getFirstName()).isEqualTo(alex.getFirstName());
    }

}
