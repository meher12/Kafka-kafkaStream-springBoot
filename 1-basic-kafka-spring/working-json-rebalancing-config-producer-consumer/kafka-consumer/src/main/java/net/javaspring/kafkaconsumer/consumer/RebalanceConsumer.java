package net.javaspring.kafkaconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RebalanceConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RebalanceConsumer.class);

    @KafkaListener(topics = "t_rebalance", concurrency = "3", groupId = "rebalance_consumer")
    public void consume(ConsumerRecord<String, String> message) {
        LOGGER.info("Partition: {}, Offset: {}, Message: {}",
                message.partition(), message.offset(), message.value());
    }
}
