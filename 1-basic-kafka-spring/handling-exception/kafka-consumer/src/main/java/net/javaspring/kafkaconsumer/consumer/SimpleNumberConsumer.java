package net.javaspring.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaconsumer.entity.SimpleNumber;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SimpleNumberConsumer {

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "t_simple_number", groupId = "cg-simple_number")
    public void consumer(String message) throws JsonProcessingException {
        var simpleNumber = objectMapper.readValue(message, SimpleNumber.class);
        if (simpleNumber.getNumber() %2 != 0) {
            throw new IllegalArgumentException("Odd number");
        }
        log.info("Valid number : {}", simpleNumber);
    }
}
