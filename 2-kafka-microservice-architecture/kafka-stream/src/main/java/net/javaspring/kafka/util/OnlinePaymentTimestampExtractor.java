package net.javaspring.kafka.util;

import net.javaspring.kafka.broker.message.OnlinePaymentMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.LocalDateTime;

public class OnlinePaymentTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var onlinePaymentMessage  = (OnlinePaymentMessage) record.value();
       // onlinePaymentMessage.setOnlinePaymentDateTime(LocalDateTime.now());
        return onlinePaymentMessage != null ? LocalDateTimeUtil.toEpochTimestamp(onlinePaymentMessage.getOnlinePaymentDateTime()) : record.timestamp();
    }
}
