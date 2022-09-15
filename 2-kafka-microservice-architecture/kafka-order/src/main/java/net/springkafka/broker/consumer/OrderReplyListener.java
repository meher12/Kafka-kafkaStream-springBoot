package net.springkafka.broker.consumer;

import lombok.extern.slf4j.Slf4j;
import net.springkafka.broker.message.OrderReplyMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
// consumer for reward reply producer
public class OrderReplyListener {

    @KafkaListener(topics = "t.commodity.order-reply")
    public void listen(OrderReplyMessage message){
        log.info("Reply message received : {}", message);

    }
}
