package net.javaspring.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaconsumer.entity.Commodity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

//@Service
@Slf4j
public class CommodityDashboardConsumer {

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "t_commodity", groupId = "cg-dashboard")
    public void consume(String message) throws JsonProcessingException, InterruptedException {
        var commodity  = objectMapper.readValue(message, Commodity.class);

        Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
        log.info("Dashboard logic for {}", commodity);
    }
}
