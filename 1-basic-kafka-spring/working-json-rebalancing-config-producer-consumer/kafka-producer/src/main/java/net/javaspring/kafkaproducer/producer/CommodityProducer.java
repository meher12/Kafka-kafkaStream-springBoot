package net.javaspring.kafkaproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaproducer.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class CommodityProducer {

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public CommodityProducer(KafkaTemplate<String, String> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Commodity commodity) throws JsonProcessingException {
        var jsonCommodity = objectMapper.writeValueAsString(commodity);
        log.info("Commodity {} is sent", jsonCommodity);
        kafkaTemplate.send("t_commodity", commodity.getName(), jsonCommodity);
    }
}
