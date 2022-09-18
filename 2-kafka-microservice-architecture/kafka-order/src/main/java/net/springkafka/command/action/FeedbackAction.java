package net.springkafka.command.action;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.FeedbackRequest;
import net.springkafka.broker.message.DiscountMessage;
import net.springkafka.broker.message.FeedbackMessage;
import net.springkafka.broker.producer.DiscountProducer;
import net.springkafka.broker.producer.FeedbackProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedbackAction {

    @Autowired
    private FeedbackProducer feedbackProducer;

    public void publishToKafka(FeedbackRequest request) {

        var message = new FeedbackMessage(request.getBranchLocation(), request.getFeedbackDateTime(),
                request.getRating(), request.getFeedback());
        feedbackProducer.publish(message);
    }
}
