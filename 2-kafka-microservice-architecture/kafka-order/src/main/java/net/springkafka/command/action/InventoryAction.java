package net.springkafka.command.action;

import net.springkafka.api.request.FeedbackRequest;
import net.springkafka.api.request.InventoryRequest;
import net.springkafka.broker.message.FeedbackMessage;
import net.springkafka.broker.message.InventoryMessage;
import net.springkafka.broker.producer.FeedbackProducer;
import net.springkafka.broker.producer.InventoryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryAction {

    @Autowired
    private InventoryProducer inventoryProducer;

    public void publishToKafka(InventoryRequest request) {

        var message = new InventoryMessage(request.getLocation(), request.getItem(),
                request.getQuantity(), request.getTransactionTime());
        inventoryProducer.publish(message);
    }
}
