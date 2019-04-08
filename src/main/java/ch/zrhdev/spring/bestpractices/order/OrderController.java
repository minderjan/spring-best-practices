package ch.zrhdev.spring.bestpractices.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    // ------ Endpoint: /orders --------------

    @GetMapping
    public ResponseEntity getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody CustomerOrder customerOrder) {
        return new ResponseEntity<>(orderService.createOrder(customerOrder), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateCustomer(@RequestBody CustomerOrder customerOrder) {
        orderService.updateOrder(customerOrder);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // ------ Endpoint: /orders/{id} --------------

    @GetMapping(value = "/{id}")
    public ResponseEntity getCustomerById(@PathVariable( "id" ) Long orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateCustomerById(@RequestBody CustomerOrder customerOrder, @PathVariable( "id" ) Long orderId) {
        return new ResponseEntity<>(orderService.updateOrderById(customerOrder, orderId), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteOrder(@PathVariable( "id" ) Long orderId) {
        orderService.deleteOrderById(orderId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
