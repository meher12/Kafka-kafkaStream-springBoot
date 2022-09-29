package net.springkafka.broker.producer;


import net.springkafka.broker.message.PremiumUserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class PremiumUserProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PremiumUserProducer.class);

    @Autowired
    private KafkaTemplate<String, PremiumUserMessage> kafkaTemplate;

    public void publish(PremiumUserMessage message){

        kafkaTemplate.send("t.commodity.premium-user", message.getUsername(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, PremiumUserMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Premium user {}, failed to published ", message.getUsername());
                    }

                    @Override
                    public void onSuccess(SendResult<String, PremiumUserMessage> result) {
                        LOGGER.info("Premium user: {}, published successfully ", message.getUsername());
                    }
                });


    }
}
