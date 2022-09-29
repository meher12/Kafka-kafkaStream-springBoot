package net.javaspring.kafka.broker.message;

import lombok.Data;

@Data
public class PremiumOfferMessage {

    private String username;
    private String level;
    private String purchaseNumber;
}
