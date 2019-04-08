package ch.zrhdev.spring.bestpractices.boot;

import ch.zrhdev.spring.bestpractices.customer.Customer;
import ch.zrhdev.spring.bestpractices.customer.CustomerService;
import ch.zrhdev.spring.bestpractices.order.OrderRepository;
import com.github.javafaker.Faker;
import ch.zrhdev.spring.bestpractices.customer.CustomerRepository;
import ch.zrhdev.spring.bestpractices.order.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Profile("local")
@Component
public class MockDataSetup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    private Faker faker = new Faker(new Locale("de-CH"));


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // Delete all Customers and their orders in database
        orderRepository.deleteAll();
        customerRepository.deleteAll();

        // Create 20 new customers
        for (int i = 0;i<20;i++) {
            customerService.createCustomer(new Customer(faker.name().firstName(),faker.name().lastName()));
        }

        // Create for each Customer 5 Orders
        for (Customer customer:customerService.getAllCustomers()) {
            for(int i = 0; i<5; i++) {
                orderRepository.save(new CustomerOrder(faker.date().past(2, TimeUnit.DAYS), faker.rickAndMorty().character(), customer));
            }
        }
    }
}
