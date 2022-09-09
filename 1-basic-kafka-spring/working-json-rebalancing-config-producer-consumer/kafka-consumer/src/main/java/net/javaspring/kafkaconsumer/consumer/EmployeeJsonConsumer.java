package net.javaspring.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaconsumer.entity.Employee;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class EmployeeJsonConsumer {

    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "t_employee", groupId = "json_consumer")
    public void consumer(String message) throws JsonProcessingException {
        var emp = objectMapper.readValue(message, Employee.class);
        log.info("Employee is {}", emp);
    }
}
