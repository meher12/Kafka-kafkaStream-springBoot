package net.javaspring.kafkaproducer.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class RebalanceProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public RebalanceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private int i = 0;

    @Scheduled(fixedRate = 1000)
    public void sendMessage() {
        i++;
        log.info(String.format("Counter is %s", i));
        kafkaTemplate.send("t_rebalance", "Counter is: " + i);
    }
}
