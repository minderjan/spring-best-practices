package ch.zrhdev.spring.bestpractices.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    public CustomerOrder createOrder(CustomerOrder customerOrder) {
        return this.saveOrUpdateOrder(customerOrder);
    }

    public CustomerOrder getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("The order with order_id="
                + orderId + " could not be found in repository"));
    }

    public List<CustomerOrder> getAllOrders() {
        LOGGER.debug("Return all orders from repository");
        return orderRepository.findAll();
    }

    public CustomerOrder updateOrder(CustomerOrder customerOrder) {
        LOGGER.debug("CustomerOrder with id order_id={} will be updated", customerOrder.getOrderId());
        return this.saveOrUpdateOrder(customerOrder);
    }

    public CustomerOrder updateOrderById(CustomerOrder customerOrder, Long orderId) {
        // If you want to modify the single fields first
        CustomerOrder customerOrderToUpdate = this.getOrderById(orderId);
        customerOrderToUpdate.setOrderDate(customerOrder.getOrderDate());
        customerOrderToUpdate.setOrderSummary(customerOrder.getOrderSummary());
        return this.saveOrUpdateOrder(customerOrderToUpdate);
    }

    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
        LOGGER.debug("order with id order_id={} deleted", orderId);
    }

    private CustomerOrder saveOrUpdateOrder(CustomerOrder customerOrder) {
        LOGGER.debug("Save new customerOrder: {}", customerOrder);
        return orderRepository.save(customerOrder);
    }
}
