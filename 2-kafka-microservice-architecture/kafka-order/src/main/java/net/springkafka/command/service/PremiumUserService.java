package net.springkafka.command.service;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.PremiumUser;
import net.springkafka.command.action.DiscountAction;
import net.springkafka.command.action.PremiumUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PremiumUserService {

    @Autowired
    private PremiumUserAction premiumUserAction;

    public void createUser(PremiumUser request){

        premiumUserAction.publishToKafka(request);

    }
}
