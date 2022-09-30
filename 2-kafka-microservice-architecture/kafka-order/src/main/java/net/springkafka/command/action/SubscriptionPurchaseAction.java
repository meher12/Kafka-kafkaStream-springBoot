package net.springkafka.command.action;

import net.springkafka.api.request.PremiumPurchase;
import net.springkafka.api.request.SubscriptionPurchase;
import net.springkafka.broker.message.PremiumPurchaseMessage;
import net.springkafka.broker.message.SubscriptionPurchaseMessage;
import net.springkafka.broker.producer.PremiumPurchaseProducer;
import net.springkafka.broker.producer.SubscriptionPurchaseProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPurchaseAction {

    @Autowired
    private SubscriptionPurchaseProducer subscriptionPurchaseProducer;

    public void publishToKafka(SubscriptionPurchase request) {

        var message = new SubscriptionPurchaseMessage(request.getSubscriptionNumber(), request.getUsername());
        subscriptionPurchaseProducer.publish(message);
    }
}
