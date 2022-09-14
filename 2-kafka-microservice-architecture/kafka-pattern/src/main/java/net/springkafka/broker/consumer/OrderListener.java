package net.springkafka.broker.consumer;

import net.springkafka.broker.message.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderListener.class);

    @KafkaListener(topics = "t.commodity.order", groupId = "cg-pattern")
    public void listen(OrderMessage message){
        var totalItemAmount= message.getPrice() * message.getQuantity();

        LOGGER.info("Processing order {}, item {}, credit card number {}, Total amount for this item is {}",
                message.getOrderNumber(), message.getItemName(),
                message.getCreditCardNumber(), totalItemAmount);
    }
}
