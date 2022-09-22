package net.springkafka.broker.producer;

import net.springkafka.broker.message.DiscountMessage;
import net.springkafka.broker.message.FlashSaleVoteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class FlashSaleVoteProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlashSaleVoteProducer.class);

    @Autowired
    private KafkaTemplate<String, FlashSaleVoteMessage> kafkaTemplate;

    public void publish(FlashSaleVoteMessage message){

        kafkaTemplate.send("t.commodity.flashsale.vote", message.getCustomerId(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, FlashSaleVoteMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Customer Id {}, failed to published ", message.getCustomerId());
                    }

                    @Override
                    public void onSuccess(SendResult<String, FlashSaleVoteMessage> result) {
                        LOGGER.info("FlashSaleVote id: {}, Flash Sale itemName: {} published successfully ", message.getCustomerId(),
                                message.getItemName());
                    }
                });


    }
}
