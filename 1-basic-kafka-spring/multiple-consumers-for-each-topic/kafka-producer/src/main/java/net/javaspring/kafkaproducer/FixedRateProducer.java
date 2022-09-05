package net.javaspring.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
public class FixedRateProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public FixedRateProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private int i = 0;

    private Logger log = LoggerFactory.getLogger(FixedRateProducer.class);

    @Scheduled(fixedRate = 1000)
    public void sendMessage() {
        i++;
        log.info("i is " + i);
        kafkaTemplate.send("t_fixedrate_2", "Fixed rate " + i);
    }


}
