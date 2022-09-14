package net.springkafka.api.request;

public class DiscountRequest {

    private String discountCode;
    private String discountPercentage;

    public DiscountRequest() {
    }

    public DiscountRequest(String discountCode, String discountPercentage) {
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
        return "DiscountRequest{" +
                "discountCode='" + discountCode + '\'' +
                ", discountPercentage='" + discountPercentage + '\'' +
                '}';
    }
}
