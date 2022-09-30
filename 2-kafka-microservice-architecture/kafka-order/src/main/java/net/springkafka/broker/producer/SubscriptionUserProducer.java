package net.springkafka.broker.producer;


import net.springkafka.broker.message.PremiumUserMessage;
import net.springkafka.broker.message.SubscriptionUserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class SubscriptionUserProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionUserProducer.class);

    @Autowired
    private KafkaTemplate<String, SubscriptionUserMessage> kafkaTemplate;

    public void publish(SubscriptionUserMessage message){

        kafkaTemplate.send("t.commodity.subscription-user", message.getUsername(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, SubscriptionUserMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Subscription user {}, failed to published ", message.getUsername());
                    }

                    @Override
                    public void onSuccess(SendResult<String, SubscriptionUserMessage> result) {
                        LOGGER.info("Subscription user: {}, published successfully ", message.getUsername());
                    }
                });


    }
}
