package net.springkafka.broker.message;

public class DiscountMessage {

    private String discountCode;
    private String discountPercentage;

    public DiscountMessage() {
    }

    public DiscountMessage(String discountCode, String discountPercentage) {
        this.discountCode = discountCode;
        this.discountPercentage = discountPercentage;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "DiscountMessage{" +
                "discountCode='" + discountCode + '\'' +
                ", discountPercentage='" + discountPercentage + '\'' +
                '}';
    }
}
