package net.springkafka.command.action;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.FlashSaleVoteRequest;
import net.springkafka.broker.message.DiscountMessage;
import net.springkafka.broker.message.FlashSaleVoteMessage;
import net.springkafka.broker.producer.DiscountProducer;
import net.springkafka.broker.producer.FlashSaleVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlashSaleVoteAction {

    @Autowired
    private FlashSaleVoteProducer flashSaleVoteProducer;

    public void publishToKafka(FlashSaleVoteRequest request) {

        var message = new FlashSaleVoteMessage(request.getCustomerId(), request.getItemName());
        flashSaleVoteProducer.publish(message);
    }
}
