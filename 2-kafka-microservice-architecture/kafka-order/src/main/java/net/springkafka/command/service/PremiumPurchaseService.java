package net.springkafka.command.service;

import net.springkafka.api.request.PremiumPurchase;
import net.springkafka.api.request.PremiumUser;
import net.springkafka.command.action.PremiumPurchaseAction;
import net.springkafka.command.action.PremiumUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PremiumPurchaseService {

    @Autowired
    private PremiumPurchaseAction premiumPurchaseAction;

    public void createPurchase(PremiumPurchase request){

        premiumPurchaseAction.publishToKafka(request);

    }
}
