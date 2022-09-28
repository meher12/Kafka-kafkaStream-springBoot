package net.springkafka.command.action;

import net.springkafka.api.request.WebColorVote;
import net.springkafka.api.request.WebLayoutVote;
import net.springkafka.broker.message.WebColorVoteMessage;
import net.springkafka.broker.message.WebLayoutVoteMessage;
import net.springkafka.broker.producer.WebColorVoteProducer;
import net.springkafka.broker.producer.WebLayoutVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebLayoutVoteAction {

    @Autowired
    private WebLayoutVoteProducer webLayoutVoteProducer;

    public void publishToKafka(WebLayoutVote request) {
        var message = new WebLayoutVoteMessage(request.getUsername(),request.getLayout());
        webLayoutVoteProducer.publish(message);
    }
}
