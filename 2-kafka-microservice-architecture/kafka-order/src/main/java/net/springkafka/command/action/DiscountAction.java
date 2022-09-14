package net.springkafka.command.action;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.PromotionRequest;
import net.springkafka.broker.message.DiscountMessage;
import net.springkafka.broker.message.PromotionMessage;
import net.springkafka.broker.producer.DiscountProducer;
import net.springkafka.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountAction {

    @Autowired
    private DiscountProducer discountProducer;

    public void publishToKafka(DiscountRequest request) {

        var message = new DiscountMessage(request.getDiscountCode(), request.getDiscountCode());
        discountProducer.publish(message);
    }
}
