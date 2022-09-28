package net.springkafka.command.action;

import net.springkafka.api.request.OnlinePaymentRequest;
import net.springkafka.api.request.WebColorVote;
import net.springkafka.broker.message.OnlinePaymentMessage;
import net.springkafka.broker.message.WebColorVoteMessage;
import net.springkafka.broker.producer.OnlinePaymentProducer;
import net.springkafka.broker.producer.WebColorVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebColorVoteAction {

    @Autowired
    private WebColorVoteProducer webColorVoteProducer;

    public void publishToKafka(WebColorVote request) {
        var message = new WebColorVoteMessage(request.getUsername(),request.getColor());
        webColorVoteProducer.publish(message);
    }
}
