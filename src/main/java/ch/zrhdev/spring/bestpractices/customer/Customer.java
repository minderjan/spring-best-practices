package ch.zrhdev.spring.bestpractices.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ch.zrhdev.spring.bestpractices.order.CustomerOrder;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * The Customer Entity represents a generic customer from an online shop
 *
 * This Entity does not contain Boilerplate code. Done by lombok.
 */
@Entity @Data @NoArgsConstructor @RequiredArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long customerId;

    @NonNull
    @Column(nullable = false)
    private String firstName;

    @NonNull
    @Column(nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties({ "customer"})
    private List<CustomerOrder> orderList;
}
