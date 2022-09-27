package net.springkafka.broker.producer;

import net.springkafka.broker.message.OnlineOrderMessage;
import net.springkafka.broker.message.OnlinePaymentMessage;
import net.springkafka.command.service.OnlinePaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Service
public class OnlinePaymentProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnlinePaymentProducer.class);

    @Autowired
    private KafkaTemplate<String, OnlinePaymentMessage> kafkaTemplate;

    public void publish(OnlinePaymentMessage message){
        /*try {
            // Future scenario
           var sendResult = kafkaTemplate.send("t.commodity.online-order", message.getPaymentNumber(), message).get();
           LOGGER.info("Send result success for message {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error publishing {}, cause {}", message, e.getMessage());
        }*/

        kafkaTemplate.send("t.commodity.online-payment", message.getPaymentNumber(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, OnlinePaymentMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("onlinePayment:  {}, failed to published ", message);
                    }

                    @Override
                    public void onSuccess(SendResult<String, OnlinePaymentMessage> result) {
                        LOGGER.info("onlinePayment:  {} published successfully ", message);
                    }
                });
    }
}
