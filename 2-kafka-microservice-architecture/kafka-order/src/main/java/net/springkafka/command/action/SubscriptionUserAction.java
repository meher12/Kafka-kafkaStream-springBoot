package net.springkafka.command.action;

import net.springkafka.api.request.PremiumUser;
import net.springkafka.api.request.SubscriptionUser;
import net.springkafka.broker.message.PremiumUserMessage;
import net.springkafka.broker.message.SubscriptionUserMessage;
import net.springkafka.broker.producer.PremiumUserProducer;
import net.springkafka.broker.producer.SubscriptionUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionUserAction {

    @Autowired
    private SubscriptionUserProducer subscriptionUserProducer;

    public void publishToKafka(SubscriptionUser request) {

        var message = new SubscriptionUserMessage(request.getUsername(), request.getDuration());
        subscriptionUserProducer.publish(message);
    }
}
