package net.springkafka.api.request;

import java.util.List;

public class OrderRequest {

    private String orderLocation;
    private String creditCardNumber;
    private List<OrderEventRequest> orderEvents;

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

    public List<OrderEventRequest> getOrderEvents() {
        return orderEvents;
    }

    public void setOrderEvents(List<OrderEventRequest> orderEvents) {
        this.orderEvents = orderEvents;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderLocation='" + orderLocation + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", orderEvents=" + orderEvents +
                '}';
    }
}
