package net.javaspring.kafka.broker.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.javaspring.kafka.util.LocalDateTimeDeserializer;
import net.javaspring.kafka.util.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class OrderMessage {

    private String creditCardNumber;
    private String itemName;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime orderDateTime;
    private String orderLocation;
    private String orderNumber;
    private int price;
    private int quantity;

    public OrderMessage copy() {
        var copy = new OrderMessage();

        copy.setCreditCardNumber(this.creditCardNumber);
        copy.setItemName(this.itemName);
        copy.setOrderDateTime(this.orderDateTime);
        copy.setOrderLocation(this.orderLocation);
        copy.setOrderNumber(this.orderNumber);
        copy.setPrice(this.price);
        copy.setQuantity(this.quantity);
        return copy;
    }


    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "creditCardNumber='" + creditCardNumber + '\'' +
                ", itemName='" + itemName + '\'' +
                ", orderDateTime=" + orderDateTime +
                ", orderLocation='" + orderLocation + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
