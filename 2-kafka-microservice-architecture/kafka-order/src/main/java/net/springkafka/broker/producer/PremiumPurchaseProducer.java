package net.springkafka.broker.producer;


import net.springkafka.broker.message.PremiumPurchaseMessage;
import net.springkafka.broker.message.PremiumUserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class PremiumPurchaseProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PremiumPurchaseProducer.class);

    @Autowired
    private KafkaTemplate<String, PremiumPurchaseMessage> kafkaTemplate;

    public void publish(PremiumPurchaseMessage message){

        kafkaTemplate.send("t.commodity.premium-purchase", message.getPurchaseNumber(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, PremiumPurchaseMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Premium purchase {}, failed to published ", message.getPurchaseNumber());
                    }

                    @Override
                    public void onSuccess(SendResult<String, PremiumPurchaseMessage> result) {
                        LOGGER.info("Premium purchase: {}, published successfully ", message.getPurchaseNumber());
                    }
                });


    }
}
