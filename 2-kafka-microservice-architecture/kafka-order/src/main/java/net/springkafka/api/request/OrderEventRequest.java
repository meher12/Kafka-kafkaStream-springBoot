package net.springkafka.api.request;

import javax.persistence.Column;

public class OrderEventRequest {

    private String orderEventNameRq;
    private int orderEventPriceRq;
    private int orderEventQuantityRq;

    public String getOrderEventNameRq() {
        return orderEventNameRq;
    }

    public void setOrderEventNameRq(String orderEventNameRq) {
        this.orderEventNameRq = orderEventNameRq;
    }

    public int getOrderEventPriceRq() {
        return orderEventPriceRq;
    }

    public void setOrderEventPriceRq(int orderEventPriceRq) {
        this.orderEventPriceRq = orderEventPriceRq;
    }

    public int getOrderEventQuantityRq() {
        return orderEventQuantityRq;
    }

    public void setOrderEventQuantityRq(int orderEventQuantityRq) {
        this.orderEventQuantityRq = orderEventQuantityRq;
    }

    @Override
    public String toString() {
        return "OrderEventRequest{" +
                "orderEventNameRq='" + orderEventNameRq + '\'' +
                ", orderEventPriceRq=" + orderEventPriceRq +
                ", orderEventQuantityRq=" + orderEventQuantityRq +
                '}';
    }
}
