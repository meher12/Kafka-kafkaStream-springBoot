package net.springkafka.command.service;

import net.springkafka.api.request.OnlineOrderRequest;
import net.springkafka.api.request.OnlinePaymentRequest;
import net.springkafka.command.action.OnlineOrderAction;
import net.springkafka.command.action.OnlinePaymentAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlinePaymentService {

    @Autowired
    private OnlinePaymentAction onlinePaymentAction;

    public void createOnlinePayment(OnlinePaymentRequest request){

        onlinePaymentAction.publishToKafka(request);

    }
}
