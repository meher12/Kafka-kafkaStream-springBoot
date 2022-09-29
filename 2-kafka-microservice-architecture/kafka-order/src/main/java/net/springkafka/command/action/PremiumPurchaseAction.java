package net.springkafka.command.action;

import net.springkafka.api.request.PremiumPurchase;
import net.springkafka.api.request.PremiumUser;
import net.springkafka.broker.message.PremiumPurchaseMessage;
import net.springkafka.broker.message.PremiumUserMessage;
import net.springkafka.broker.producer.PremiumPurchaseProducer;
import net.springkafka.broker.producer.PremiumUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PremiumPurchaseAction {

    @Autowired
    private PremiumPurchaseProducer premiumPurchaseProducer;

    public void publishToKafka(PremiumPurchase request) {

        var message = new PremiumPurchaseMessage(request.getPurchaseNumber(), request.getUsername());
        premiumPurchaseProducer.publish(message);
    }
}
