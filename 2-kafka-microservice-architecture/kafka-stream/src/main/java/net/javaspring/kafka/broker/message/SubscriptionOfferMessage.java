package net.javaspring.kafka.broker.message;

import lombok.Data;

@Data
public class SubscriptionOfferMessage {

    private String username;
    private String duration;
    private String subscriptionNumber;
}
