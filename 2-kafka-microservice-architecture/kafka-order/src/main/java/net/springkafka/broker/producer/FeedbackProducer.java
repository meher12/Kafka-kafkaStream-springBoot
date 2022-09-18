package net.springkafka.broker.producer;

import net.springkafka.broker.message.DiscountMessage;
import net.springkafka.broker.message.FeedbackMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class FeedbackProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackProducer.class);

    @Autowired
    private KafkaTemplate<String, FeedbackMessage> kafkaTemplate;

    public void publish(FeedbackMessage message){

        kafkaTemplate.send("t.commodity.feedback-one", message.getBranchLocation(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, FeedbackMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Feedback {}, failed to published ", message.getFeedback());
                    }

                    @Override
                    public void onSuccess(SendResult<String, FeedbackMessage> result) {
                        LOGGER.info("Feedback Location:  {} published successfully ", message.getBranchLocation()
                                );
                    }
                });


    }
}
