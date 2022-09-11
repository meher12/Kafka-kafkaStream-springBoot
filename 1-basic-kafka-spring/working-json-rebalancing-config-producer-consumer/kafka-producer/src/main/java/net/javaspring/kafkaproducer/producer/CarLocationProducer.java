package net.javaspring.kafkaproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaproducer.entity.CarLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarLocationProducer {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public CarLocationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(CarLocation carLocation) throws JsonProcessingException {
        var jsonCarLocation = mapper.writeValueAsString(carLocation);

        log.info("Car location sent is {}", jsonCarLocation);
        kafkaTemplate.send("t_location",carLocation.getCarId(),jsonCarLocation);
    }
}
