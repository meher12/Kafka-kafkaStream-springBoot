package net.springkafka.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private int orderId;

    @Column(nullable = false, length = 20)
    private String orderNumber;

    @Column(nullable = false, length = 20)
    private String creditCartNumber;

    @Column(nullable = false, length = 200)
    private String orderLocation;



    @Column(nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(int orderId, String orderNumber, String creditCartNumber, String orderLocation,
                 LocalDateTime orderDateTime,  List<OrderItem> orderItemList) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.creditCartNumber = creditCartNumber;
        this.orderLocation = orderLocation;
        this.orderDateTime = orderDateTime;
        this.orderItems = orderItemList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCreditCartNumber() {
        return creditCartNumber;
    }

    public void setCreditCartNumber(String creditCartNumber) {
        this.creditCartNumber = creditCartNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItemList) {
        this.orderItems = orderItemList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderNumber='" + orderNumber + '\'' +
                ", creditCartNumber='" + creditCartNumber + '\'' +
                ", orderLocation='" + orderLocation + '\'' +
                ", orderDateTime=" + orderDateTime +
                ", orderEventList=" + orderItems +
                '}';
    }
}
