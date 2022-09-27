package net.javaspring.kafka.util;


import net.javaspring.kafka.broker.message.OnlineOrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.LocalDateTime;


public class OnlineOrderTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {

        var onlineOrderMessage = (OnlineOrderMessage) record.value();
       // onlineOrderMessage.setOnlineOrderDateTime(LocalDateTime.now());
        return onlineOrderMessage != null ? LocalDateTimeUtil.toEpochTimestamp(onlineOrderMessage.getOnlineOrderDateTime()) : record.timestamp();
        /*
        // convert ZonedDateTime to Instant to Timestamp
        Timestamp ts = Timestamp.from(ZonedDateTime.now().toInstant());
        // convert Timestamp to toEpochMilli
        var instant = ts.toInstant().toEpochMilli();
      var onlineOrderMessage = (OnlineOrderMessage) record.value();
        return onlineOrderMessage != null ? instant : record.timestamp();
        */


       /* long timestamp = -1;
        val onlineOrderMessage = (OnlineOrderMessage) record.value();
        if (onlineOrderMessage != null) {
            timestamp = onlineOrderMessage.getOnlineOrderDateTime();
        }
        if (timestamp < 0) {
            // Invalid timestamp!  Attempt to estimate a new timestamp,
            // otherwise fall back to wall-clock time (processing-time).
            if (partitionTime >= 0) {
                return partitionTime;
            } else {
                return System.currentTimeMillis();
            }
        }
        return timestamp;*/

    }

  /*  private static final Logger LOG = LogManager.getLogger( OnlineOrderTimestampExtractor.class );

    @Override
    public long extract ( ConsumerRecord<Object, Object> consumerRecord, long previousTimestamp ) {
        final long timestamp = consumerRecord.timestamp();

        if ( timestamp < 0 ) {
            final String msg = consumerRecord.toString().trim();
            LOG.warn( "Record has wrong Kafka timestamp: {}. It will be patched with local timestamp. Details: {}", timestamp, msg );
            return System.currentTimeMillis();
        }

        return timestamp;
    }*/
}
