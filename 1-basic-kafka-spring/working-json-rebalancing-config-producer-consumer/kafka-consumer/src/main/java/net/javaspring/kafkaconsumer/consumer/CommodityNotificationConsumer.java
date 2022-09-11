package net.javaspring.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaconsumer.entity.Commodity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class CommodityNotificationConsumer {


    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "t_commodity", groupId = "cg-notification")
    public void consume(String message) throws JsonProcessingException {
        var commodity  = objectMapper.readValue(message, Commodity.class);
        log.info("Notification logic for {}", commodity);
    }
}
