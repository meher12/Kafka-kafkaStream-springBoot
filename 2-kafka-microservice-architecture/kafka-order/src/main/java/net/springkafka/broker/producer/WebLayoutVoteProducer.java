package net.springkafka.broker.producer;

import net.springkafka.broker.message.WebColorVoteMessage;
import net.springkafka.broker.message.WebLayoutVoteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class WebLayoutVoteProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLayoutVoteProducer.class);

    @Autowired
    private KafkaTemplate<String, WebLayoutVoteMessage> kafkaTemplate;

    public void publish(WebLayoutVoteMessage message) {
        try {
            // Future scenario
            var sendResult = kafkaTemplate.send("t.commodity.web.vote-layout", message.getUsername(), message).get();
            LOGGER.info("Send result success for message {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error publishing {}, cause {}", message, e.getMessage());
        }


    }
}
