package net.springkafka.command.service;

import net.springkafka.api.request.OnlineOrderRequest;
import net.springkafka.api.request.PromotionRequest;
import net.springkafka.command.action.OnlineOrderAction;
import net.springkafka.command.action.PromotionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineOrderService {

    @Autowired
    private OnlineOrderAction onlineOrderAction;

    public void createOnlineOrder(OnlineOrderRequest request){

        onlineOrderAction.publishToKafka(request);

    }
}
