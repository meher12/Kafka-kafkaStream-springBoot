package net.javaspring.kafka.util;

import net.javaspring.kafka.broker.message.WebColorVoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.LocalDateTime;

public class WebColorVoteTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {

        var message = (WebColorVoteMessage) record.value();
        return message != null
                ? LocalDateTimeUtil.toEpochTimestamp(LocalDateTime.now())
                : record.timestamp();
    }
}
