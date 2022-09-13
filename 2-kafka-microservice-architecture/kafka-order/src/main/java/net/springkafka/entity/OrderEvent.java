package net.springkafka.entity;

import javax.persistence.*;

@Entity
@Table(name="order_event")
public class OrderEvent {

    @Id
    @GeneratedValue
    private int orderEventId;

    @Column(nullable = false, length = 200)
    private String orderEventName;

    @Column(nullable = false)
    private int orderEventPrice;

    @Column(nullable = false)
    private int orderEventQuantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderEvent() {
    }

    public OrderEvent(int orderEventId, String orderEventName, int orderEventPrice, int orderEventQuantity, Order order) {
        this.orderEventId = orderEventId;
        this.orderEventName = orderEventName;
        this.orderEventPrice = orderEventPrice;
        this.orderEventQuantity = orderEventQuantity;
        this.order = order;
    }

    public int getOrderEventId() {
        return orderEventId;
    }

    public void setOrderEventId(int orderEventId) {
        this.orderEventId = orderEventId;
    }

    public String getOrderEventName() {
        return orderEventName;
    }

    public void setOrderEventName(String orderEventName) {
        this.orderEventName = orderEventName;
    }

    public int getOrderEventPrice() {
        return orderEventPrice;
    }

    public void setOrderEventPrice(int orderEventPrice) {
        this.orderEventPrice = orderEventPrice;
    }

    public int getOrderEventQuantity() {
        return orderEventQuantity;
    }

    public void setOrderEventQuantity(int orderEventQuantity) {
        this.orderEventQuantity = orderEventQuantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderEventId=" + orderEventId +
                ", orderEventName='" + orderEventName + '\'' +
                ", orderEventPrice=" + orderEventPrice +
                ", orderEventQuantity=" + orderEventQuantity +
                ", order=" + order +
                '}';
    }
}
