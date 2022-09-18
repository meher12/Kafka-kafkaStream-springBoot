package net.springkafka.command.service;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.FeedbackRequest;
import net.springkafka.command.action.DiscountAction;
import net.springkafka.command.action.FeedbackAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackAction feedbackAction;

    public void createFeedback(FeedbackRequest request){

        feedbackAction.publishToKafka(request);

    }
}
