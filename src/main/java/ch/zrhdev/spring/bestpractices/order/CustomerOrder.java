package ch.zrhdev.spring.bestpractices.order;

import ch.zrhdev.spring.bestpractices.customer.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long orderId;

    // If you want to use a specific date format, define it here. Otherwise use the default timestamp value -> epoch time
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date orderDate;
    private String orderSummary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="customer_id")
    @JsonIgnoreProperties({ "orderList"})
    private Customer customer;

    public CustomerOrder() {}

    public CustomerOrder(Date orderDate, String orderSummary, Customer customer) {
        this.orderDate = orderDate;
        this.orderSummary = orderSummary;
        this.customer = customer;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(String orderSummary) {
        this.orderSummary = orderSummary;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + orderId +
                ", orderDate=" + orderDate +
                ", orderSummary='" + orderSummary + '\'' +
                '}';
    }
}
