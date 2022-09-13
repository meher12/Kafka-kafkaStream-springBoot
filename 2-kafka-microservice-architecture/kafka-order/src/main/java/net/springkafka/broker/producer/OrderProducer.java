package net.springkafka.broker.producer;

import net.springkafka.broker.message.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    public void publish(OrderMessage message){

        // Callback scenario
        kafkaTemplate.send("t.commodity.order", message.getOrderNumber(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, OrderMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Order {}, item {} failed to published ", message.getOrderNumber(),
                                message.getEventName());
                    }

                    @Override
                    public void onSuccess(SendResult<String, OrderMessage> result) {

                        LOGGER.info("Just a dummy message for order {}, item {} published successfully ", message.getOrderNumber(),
                                message.getEventName());

                    }
                });
    }
}
