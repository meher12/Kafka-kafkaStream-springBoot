package net.springkafka.broker.producer;

import net.springkafka.broker.message.OrderMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);
    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage message) {
        int surpriseBonus = StringUtils.startsWithIgnoreCase(message.getOrderLocation(), "A") ? 25 : 15;

        List<Header> headers = new ArrayList<>();
        var surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(surpriseBonus).getBytes());
        headers.add(surpriseBonusHeader);

        return new ProducerRecord<String, OrderMessage>("t.commodity.order", null, message.getOrderNumber(),
                message, headers);

    }

    public void publish(OrderMessage message) {

        // header
        var producerRecord = buildProducerRecord(message);
        // Callback scenario
       // kafkaTemplate.send("t.commodity.order", message.getOrderNumber(), message)
        kafkaTemplate.send(producerRecord)
                .addCallback(new ListenableFutureCallback<SendResult<String, OrderMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        LOGGER.error("Order {}, item {} failed to published ", message.getOrderNumber(),
                                message.getItemName());
                    }

                    @Override
                    public void onSuccess(SendResult<String, OrderMessage> result) {

                        LOGGER.info("Just a dummy message for order {}, item {} published successfully ", message.getOrderNumber(),
                                message.getItemName());

                    }
                });
    }
}
