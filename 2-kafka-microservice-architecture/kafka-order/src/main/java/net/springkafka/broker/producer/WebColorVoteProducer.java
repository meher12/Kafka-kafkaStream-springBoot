package net.springkafka.broker.producer;

import net.springkafka.broker.message.WebColorVoteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class WebColorVoteProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebColorVoteProducer.class);

    @Autowired
    private KafkaTemplate<String, WebColorVoteMessage> kafkaTemplate;

    public void publish(WebColorVoteMessage message) {
        try {
            // Future scenario
            var sendResult = kafkaTemplate.send("t.commodity.web.vote-color", message.getUsername(), message).get();
            LOGGER.info("Send result success for message {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error publishing {}, cause {}", message, e.getMessage());
        }


    }
}
