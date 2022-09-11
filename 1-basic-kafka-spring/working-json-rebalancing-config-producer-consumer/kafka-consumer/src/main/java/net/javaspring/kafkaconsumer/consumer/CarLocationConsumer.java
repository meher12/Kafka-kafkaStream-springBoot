package net.javaspring.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaconsumer.entity.CarLocation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarLocationConsumer {

    ObjectMapper mapper = new ObjectMapper();


    @KafkaListener(topics = "t_location", groupId = "cg-all-location")
    public void listenAll(String message) throws JsonProcessingException {
        var carLocation = mapper.readValue(message, CarLocation.class);
        log.info("listenAll: {}", carLocation);
    }

    @KafkaListener(topics = "t_location", groupId = "cg-far-location", containerFactory = "farLocationContainerFactory")
    public void listenFar(String message) throws JsonProcessingException {
        var carLocation = mapper.readValue(message, CarLocation.class);

        log.info("listenFar: {}", carLocation);
    }

}
