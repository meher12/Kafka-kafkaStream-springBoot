package net.javaspring.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaKeyProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaKeyProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String key, String data) {
        kafkaTemplate.send("t_multi_partitions", key, data);
    }

}
