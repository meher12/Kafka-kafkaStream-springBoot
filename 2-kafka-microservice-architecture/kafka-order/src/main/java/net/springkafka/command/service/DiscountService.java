package net.springkafka.command.service;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.command.action.DiscountAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    private DiscountAction discountAction;

    public void createDiscount(DiscountRequest request){

        discountAction.publishToKafka(request);

    }
}
