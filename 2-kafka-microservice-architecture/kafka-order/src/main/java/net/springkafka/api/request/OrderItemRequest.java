package net.springkafka.api.request;

public class OrderItemRequest {

    private String nameRq;
    private int priceRq;
    private int quantityRq;

    public String getNameRq() {
        return nameRq;
    }

    public void setNameRq(String nameRq) {
        this.nameRq = nameRq;
    }

    public int getPriceRq() {
        return priceRq;
    }

    public void setPriceRq(int priceRq) {
        this.priceRq = priceRq;
    }

    public int getQuantityRq() {
        return quantityRq;
    }

    public void setQuantityRq(int quantityRq) {
        this.quantityRq = quantityRq;
    }

    @Override
    public String toString() {
        return "OrderItemRequest{" +
                "nameRq='" + nameRq + '\'' +
                ", priceRq=" + priceRq +
                ", quantityRq=" + quantityRq +
                '}';
    }
}
