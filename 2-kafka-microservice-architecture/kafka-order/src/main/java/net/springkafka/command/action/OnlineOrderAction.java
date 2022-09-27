package net.springkafka.command.action;

import net.springkafka.api.request.OnlineOrderRequest;
import net.springkafka.api.request.PromotionRequest;
import net.springkafka.broker.message.OnlineOrderMessage;
import net.springkafka.broker.message.PromotionMessage;
import net.springkafka.broker.producer.OnlineOrderProducer;
import net.springkafka.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnlineOrderAction {

    @Autowired
    private OnlineOrderProducer onlineOrderProducer;

    public void publishToKafka(OnlineOrderRequest request) {
        var message = new OnlineOrderMessage(request.getOnlineOrderNumber(),
                request.getAmount(), request.getUsername(), request.getOnlineOrderDateTime());
        onlineOrderProducer.publish(message);
    }
}
