package net.springkafka.command.service;

import net.springkafka.api.request.WebColorVote;
import net.springkafka.api.request.WebLayoutVote;
import net.springkafka.command.action.WebColorVoteAction;
import net.springkafka.command.action.WebLayoutVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebLayoutVoteService {

    @Autowired
    private WebLayoutVoteAction webLayoutVoteAction;

    public void createWebLayout(WebLayoutVote request){

        webLayoutVoteAction.publishToKafka(request);

    }
}
