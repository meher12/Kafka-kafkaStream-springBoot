package net.springkafka.broker.consumer;

import net.springkafka.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PromotionUppercaseListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionUppercaseListener.class);

    @KafkaListener(topics = "t.commodity.promotion-uppercase", groupId = "cg-storage", containerFactory = "kafkaListenerContainerFactory")
    public void listenPromotion(PromotionMessage message) {
        LOGGER.info("Processing uppercase promotion :: {}", message);
    }
}
