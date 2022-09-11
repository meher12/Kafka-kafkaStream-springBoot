package net.javaspring.kafkaproducer.producer;

import net.javaspring.kafkaproducer.entity.FoodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FoodOrderProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void send(FoodOrder foodOrder) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(foodOrder);
        kafkaTemplate.send("t_food_order", json);
    }

}