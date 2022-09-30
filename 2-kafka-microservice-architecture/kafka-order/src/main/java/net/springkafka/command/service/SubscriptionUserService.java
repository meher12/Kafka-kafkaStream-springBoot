package net.springkafka.command.service;

import net.springkafka.api.request.PremiumUser;
import net.springkafka.api.request.SubscriptionPurchase;
import net.springkafka.api.request.SubscriptionUser;
import net.springkafka.command.action.PremiumUserAction;
import net.springkafka.command.action.SubscriptionPurchaseAction;
import net.springkafka.command.action.SubscriptionUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionUserService {

    @Autowired
    private SubscriptionUserAction subscriptionUserAction;

    public void createUser(SubscriptionUser request){

        subscriptionUserAction.publishToKafka(request);

    }
}
