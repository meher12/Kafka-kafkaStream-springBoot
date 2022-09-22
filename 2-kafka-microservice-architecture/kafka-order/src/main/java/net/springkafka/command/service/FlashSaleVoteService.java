package net.springkafka.command.service;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.FeedbackRequest;
import net.springkafka.api.request.FlashSaleVoteRequest;
import net.springkafka.command.action.DiscountAction;
import net.springkafka.command.action.FlashSaleVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashSaleVoteService {

    @Autowired
    private FlashSaleVoteAction flashSaleVoteAction;

    public void createFlashSaleVote(FlashSaleVoteRequest request){

        flashSaleVoteAction.publishToKafka(request);

    }
}
