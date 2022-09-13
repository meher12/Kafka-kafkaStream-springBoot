package net.springkafka.command.service;

import net.springkafka.api.request.PromotionRequest;
import net.springkafka.command.action.PromotionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    @Autowired
    private PromotionAction promotionAction;

    public void createPromotion(PromotionRequest request){

        promotionAction.publishToKafka(request);

    }
}
