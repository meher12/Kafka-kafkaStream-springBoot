package net.springkafka.command.service;

import net.springkafka.api.request.OnlineOrderRequest;
import net.springkafka.api.request.WebColorVote;
import net.springkafka.command.action.OnlineOrderAction;
import net.springkafka.command.action.WebColorVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebColorVoteService {

    @Autowired
    private WebColorVoteAction webColorVoteAction;

    public void createWebColor(WebColorVote request){

        webColorVoteAction.publishToKafka(request);

    }
}
