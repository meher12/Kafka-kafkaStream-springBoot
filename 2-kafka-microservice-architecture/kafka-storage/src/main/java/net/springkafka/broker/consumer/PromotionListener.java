package net.springkafka.broker.consumer;

import net.springkafka.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "t.commodity.promotion")
public class PromotionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionListener.class);

    @KafkaHandler
    public void listenPromotion (PromotionMessage message){
        LOGGER.info("Processing promotion: {}", message);
    }
}
