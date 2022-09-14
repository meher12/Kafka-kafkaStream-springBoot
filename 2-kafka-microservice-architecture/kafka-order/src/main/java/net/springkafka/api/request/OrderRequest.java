package net.springkafka.api.request;

import java.util.List;

public class OrderRequest {

    private String orderLocation;
    private String creditCardNumber;
    private List<OrderItemRequest> orderItems;

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderLocation='" + orderLocation + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
