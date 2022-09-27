package net.springkafka.command.action;

import net.springkafka.api.request.OnlineOrderRequest;
import net.springkafka.api.request.OnlinePaymentRequest;
import net.springkafka.broker.message.OnlineOrderMessage;
import net.springkafka.broker.message.OnlinePaymentMessage;
import net.springkafka.broker.producer.OnlineOrderProducer;
import net.springkafka.broker.producer.OnlinePaymentProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnlinePaymentAction {

    @Autowired
    private OnlinePaymentProducer onlinePaymentProducer;

    public void publishToKafka(OnlinePaymentRequest request) {
        var message = new OnlinePaymentMessage(request.getPaymentNumber(),request.getPaymentMethod(), request.getOnlinePaymentDateTime());
        onlinePaymentProducer.publish(message);
    }
}
