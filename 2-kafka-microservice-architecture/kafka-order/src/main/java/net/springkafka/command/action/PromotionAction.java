package net.springkafka.command.action;

import net.springkafka.api.request.PromotionRequest;
import net.springkafka.broker.message.PromotionMessage;
import net.springkafka.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionAction {

    @Autowired
    private PromotionProducer promotionProducer;

    public void publishToKafka(PromotionRequest request) {
        var message = new PromotionMessage(request.getPromotionCodeRq());
        promotionProducer.publish(message);
    }
}
