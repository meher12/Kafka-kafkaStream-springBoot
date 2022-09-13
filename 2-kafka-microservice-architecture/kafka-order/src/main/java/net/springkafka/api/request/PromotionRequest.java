package net.springkafka.api.request;

public class PromotionRequest {

    private String promotionCodeRq;

    public PromotionRequest() {
    }

    public PromotionRequest(String promotionCodeRq) {
        this.promotionCodeRq = promotionCodeRq;
    }

    public String getPromotionCodeRq() {
        return promotionCodeRq;
    }

    public void setPromotionCodeRq(String promotionCodeRq) {
        this.promotionCodeRq = promotionCodeRq;
    }

    @Override
    public String toString() {
        return "PromotionRequest{" +
                "promotionCodeRq='" + promotionCodeRq + '\'' +
                '}';
    }
}
