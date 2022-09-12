package net.javaspring.kafkaproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaproducer.entity.SimpleNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SimpleNumberProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void send(SimpleNumber simpleNumber) throws JsonProcessingException {
        var jsonSimpleNumber = objectMapper.writeValueAsString(simpleNumber);
        log.info("Simple number sent is:  {}", jsonSimpleNumber);
        kafkaTemplate.send("t_simple_number", jsonSimpleNumber);
    }
}
