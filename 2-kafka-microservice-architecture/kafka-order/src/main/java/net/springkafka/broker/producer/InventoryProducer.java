package net.springkafka.broker.producer;

import net.springkafka.broker.message.InventoryMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class InventoryProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryProducer.class);

    @Autowired
    private KafkaTemplate<String, InventoryMessage> kafkaTemplate;

    public void publish(InventoryMessage message){

        kafkaTemplate.send("t.commodity.inventory", message.getItem(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, InventoryMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Inventory {}, failed to published ", message.getItem());
                    }

                    @Override
                    public void onSuccess(SendResult<String, InventoryMessage> result) {
                        LOGGER.info("Inventory Message:  {} published successfully ", message.getItem()
                                );
                    }
                });


    }
}
