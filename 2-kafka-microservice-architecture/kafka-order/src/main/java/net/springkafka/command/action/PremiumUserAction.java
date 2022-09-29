package net.springkafka.command.action;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.PremiumUser;
import net.springkafka.broker.message.DiscountMessage;
import net.springkafka.broker.message.PremiumUserMessage;
import net.springkafka.broker.producer.DiscountProducer;
import net.springkafka.broker.producer.PremiumUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PremiumUserAction {

    @Autowired
    private PremiumUserProducer premiumUserProducer;

    public void publishToKafka(PremiumUser request) {

        var message = new PremiumUserMessage(request.getUsername(), request.getLevel());
        premiumUserProducer.publish(message);
    }
}
