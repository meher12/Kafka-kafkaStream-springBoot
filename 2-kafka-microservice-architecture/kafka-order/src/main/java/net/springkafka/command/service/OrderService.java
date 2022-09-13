package net.springkafka.command.service;

import net.springkafka.api.request.OrderRequest;
import net.springkafka.command.action.OrderAction;
import net.springkafka.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderAction orderAction;

    public String saveOrder(OrderRequest request){
        // 1. convert OrderRequest to Order
        Order order = orderAction.convertToOrder(request);

        // 2. save Order to database
        orderAction.saveToDataBase(order);

        // 3. flatten the item & order as kafka message, and publish it
        order.getOrderEventList().forEach(orderAction::publishToKafka);

        // 4. return order number (auto generated)
        return order.getOrderNumber();
    }

}
