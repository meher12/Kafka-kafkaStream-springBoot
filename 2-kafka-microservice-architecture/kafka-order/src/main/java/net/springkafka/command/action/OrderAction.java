package net.springkafka.command.action;

import net.springkafka.api.request.OrderItemRequest;
import net.springkafka.api.request.OrderRequest;
import net.springkafka.broker.message.OrderMessage;
import net.springkafka.broker.producer.OrderProducer;
import net.springkafka.entity.Order;
import net.springkafka.entity.OrderItem;
import net.springkafka.repository.OrderItemRepository;
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
    private OrderItemRepository orderItemRepository;

    public Order convertToOrder(OrderRequest request) {
        var resultOrder = new Order();

        resultOrder.setCreditCartNumber(request.getCreditCardNumber());
        resultOrder.setOrderLocation(request.getOrderLocation());
        resultOrder.setOrderDateTime(LocalDateTime.now());
        resultOrder.setOrderNumber(RandomStringUtils.randomAlphabetic(8).toUpperCase());

        List<OrderItem> items = request.getOrderItems().stream().map(this::convertToOrderEvent)
                .collect(Collectors.toList());
        items.forEach(item -> item.setOrder(resultOrder));

        resultOrder.setOrderItems(items);
        return resultOrder;
    }

    private OrderItem convertToOrderEvent(OrderItemRequest itemRequest){
        var result  = new OrderItem();

        result.setItemName(itemRequest.getNameRq());
        result.setPrice(itemRequest.getPriceRq());
        result.setQuantity(itemRequest.getQuantityRq());

        return result;
    }

    public void saveToDataBase(Order order) {
        orderRepository.saveAndFlush(order);
        order.getOrderItems().forEach(orderItemRepository::saveAndFlush);
    }

    public void publishToKafka(OrderItem orderItem) {
        var orderMessage  = new OrderMessage();

        orderMessage.setItemName(orderItem.getItemName());
        orderMessage.setPrice(orderItem.getPrice());
        orderMessage.setQuantity(orderItem.getQuantity());

        orderMessage.setOrderDateTime(orderItem.getOrder().getOrderDateTime());
        orderMessage.setOrderLocation(orderItem.getOrder().getOrderLocation());
        orderMessage.setOrderNumber(orderItem.getOrder().getOrderNumber());
        orderMessage.setCreditCardNumber(orderItem.getOrder().getCreditCartNumber());

        orderProducer.publish(orderMessage);
    }
}
