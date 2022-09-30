package net.springkafka.command.service;

import net.springkafka.api.request.PremiumPurchase;
import net.springkafka.api.request.SubscriptionPurchase;
import net.springkafka.command.action.PremiumPurchaseAction;
import net.springkafka.command.action.SubscriptionPurchaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionPurchaseService {

    @Autowired
    private SubscriptionPurchaseAction subscriptionPurchaseAction;

    public void createPurchase(SubscriptionPurchase request){

        subscriptionPurchaseAction.publishToKafka(request);

    }
}
