package net.javaspring.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaconsumer.entity.FoodOrder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FoodOrderConsumer {

    private static final int MAX_AMOUNT_ORDER = 7;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "t_food_order", groupId = "cg-handling-exception", errorHandler = "myFoodOrderErrorHandler")
    public void consumer(String message) throws JsonProcessingException {
        FoodOrder foodOrder = objectMapper.readValue(message, FoodOrder.class);

        if (foodOrder.getAmount() > MAX_AMOUNT_ORDER) {
            throw new IllegalArgumentException("Food order amount is too many !!");
        }
        log.info("Food order valid:  {}", foodOrder);
    }
}
