package net.javaspring.kafkaproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaproducer.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class EmployeeJsonProducer {

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    public EmployeeJsonProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Employee employee) throws JsonProcessingException {

        var jsonEmployee = objectMapper.writeValueAsString(employee);
        log.info("Employee {} is sent", jsonEmployee);
        kafkaTemplate.send("t_employee", jsonEmployee);
    }


}
