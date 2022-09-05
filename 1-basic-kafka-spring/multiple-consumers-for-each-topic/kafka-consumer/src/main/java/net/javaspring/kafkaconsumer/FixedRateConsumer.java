package net.javaspring.kafkaconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class FixedRateConsumer {

    private static final Logger log = LoggerFactory.getLogger(FixedRateConsumer.class);

    //@KafkaListener(topics = "t_fixedrate_2")
    @KafkaListener(topics = "t_multi_partitions")
    public void consume(String message) {
        log.info("Consuming : {}", message);
    }
}
