package net.springkafka.broker.producer;

import net.springkafka.broker.message.DiscountMessage;
import net.springkafka.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class DiscountProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountProducer.class);

    @Autowired
    private KafkaTemplate<String, DiscountMessage> kafkaTemplate;

    public void publish(DiscountMessage message){

        kafkaTemplate.send("t.commodity.discount", message.getDiscountCode(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, DiscountMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Discount {}, failed to published ", message.getDiscountCode());
                    }

                    @Override
                    public void onSuccess(SendResult<String, DiscountMessage> result) {
                        LOGGER.info("Discount code: {}, Discount percentage: {} published successfully ", message.getDiscountCode(),
                                message.getDiscountPercentage());
                    }
                });

    }
}
