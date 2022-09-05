package net.javaspring.kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMultiConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMultiConsumer.class);

    @KafkaListener(topics = "t_multi_partitions", groupId = "default-spring-consumer", concurrency = "4")
    public void consume(ConsumerRecord<String,String> message){
        LOGGER.info("Consumer key => {},  Partition => {}, Consumer Data => {}", message.key(), message.partition(), message.value());
    }
}
