package net.springkafka.command.action;

import net.springkafka.api.request.OrderEventRequest;
import net.springkafka.api.request.OrderRequest;
import net.springkafka.broker.message.OrderMessage;
import net.springkafka.broker.producer.OrderProducer;
import net.springkafka.entity.Order;
import net.springkafka.entity.OrderEvent;
import net.springkafka.repository.OrderEventRepository;
import net.springkafka.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderAction {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventRepository orderEventRepository;

    public Order convertToOrder(OrderRequest request) {
        var resultOrder = new Order();

        resultOrder.setCreditCartNumber(request.getCreditCardNumber());
        resultOrder.setOrderLocation(request.getOrderLocation());
        resultOrder.setOrderDateTime(LocalDateTime.now());
        resultOrder.setOrderNumber(RandomStringUtils.randomAlphabetic(8).toUpperCase());

        List<OrderEvent> items = request.getOrderEvents().stream().map(this::convertToOrderEvent)
                .collect(Collectors.toList());
        items.forEach(item -> item.setOrder(resultOrder));

        resultOrder.setOrderEventList(items);
        return resultOrder;
    }

    private OrderEvent convertToOrderEvent(OrderEventRequest eventRequest){
        var result  = new OrderEvent();

        result.setOrderEventName(eventRequest.getOrderEventNameRq());
        result.setOrderEventPrice(eventRequest.getOrderEventPriceRq());
        result.setOrderEventQuantity(eventRequest.getOrderEventQuantityRq());

        return result;
    }

    public void saveToDataBase(Order order) {
        orderRepository.saveAndFlush(order);
        order.getOrderEventList().forEach(orderEventRepository::saveAndFlush);
    }

    public void publishToKafka(OrderEvent orderEvent) {
        var orderMesaage  = new OrderMessage();

        orderMesaage.setEventName(orderEvent.getOrderEventName());
        orderMesaage.setPrice(orderEvent.getOrderEventPrice());
        orderMesaage.setQuantity(orderEvent.getOrderEventQuantity());

        orderMesaage.setOrderDateTime(orderEvent.getOrder().getOrderDateTime());
        orderMesaage.setOrderLocation(orderEvent.getOrder().getOrderLocation());
        orderMesaage.setOrderNumber(orderEvent.getOrder().getOrderNumber());
        orderMesaage.setCreditCardNumber(orderEvent.getOrder().getCreditCartNumber());

        orderProducer.publish(orderMesaage);
    }
}
