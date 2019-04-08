package ch.zrhdev.spring.bestpractices.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Select by lastName without Sorting
    List<Customer> findAllByLastName(String lastName);

    // Select by firstName
    List<Customer> findAllByFirstName(String firstName);

    // Select a paged query by lastName
    Page<Customer> findByLastName(String lastName, Pageable pageable);
}
