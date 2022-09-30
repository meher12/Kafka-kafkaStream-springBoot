package net.springkafka.broker.producer;


import net.springkafka.broker.message.PremiumPurchaseMessage;
import net.springkafka.broker.message.SubscriptionPurchaseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class SubscriptionPurchaseProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionPurchaseProducer.class);

    @Autowired
    private KafkaTemplate<String, SubscriptionPurchaseMessage> kafkaTemplate;

    public void publish(SubscriptionPurchaseMessage message){

        kafkaTemplate.send("t.commodity.subscription-purchase", message.getUsername(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, SubscriptionPurchaseMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Subscription purchase {}, failed to published ", message.getSubscriptionNumber());
                    }

                    @Override
                    public void onSuccess(SendResult<String, SubscriptionPurchaseMessage> result) {
                        LOGGER.info("Subscription purchase: {}, published successfully ", message.getSubscriptionNumber());
                    }
                });


    }
}
