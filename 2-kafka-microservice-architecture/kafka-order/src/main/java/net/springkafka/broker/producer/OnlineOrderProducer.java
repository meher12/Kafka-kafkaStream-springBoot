package net.springkafka.broker.producer;

import net.springkafka.broker.message.OnlineOrderMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Service
public class OnlineOrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineOrderProducer.class);

    @Autowired
    private KafkaTemplate<String, OnlineOrderMessage> kafkaTemplate;

    public void publish(OnlineOrderMessage message){
       /* try {
            // Future scenario
           var sendResult = kafkaTemplate.send("t.commodity.online-order", message.getOnlineOrderNumber(), message).get();
           LOGGER.info("Send result success for message {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error publishing {}, cause {}", message, e.getMessage());
        }*/


        kafkaTemplate.send("t.commodity.online-order", message.getOnlineOrderNumber(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, OnlineOrderMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("onlineOrder:  {}, failed to published ", message);
                    }

                    @Override
                    public void onSuccess(SendResult<String, OnlineOrderMessage> result) {
                        LOGGER.info("onlineOrder:  {} published successfully ", message);
                    }
                });

    }
}
