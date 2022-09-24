package net.javaspring.kafka.broker.stream.inventory;


import lombok.val;
import net.javaspring.kafka.broker.message.InventoryMessage;
import net.javaspring.kafka.util.InventoryTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;

import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;
import org.apache.kafka.streams.kstream.TimeWindows;

import static org.apache.kafka.streams.kstream.Suppressed.BufferConfig.maxRecords;
import static org.apache.kafka.streams.kstream.Suppressed.BufferConfig.unbounded;
import static org.apache.kafka.streams.kstream.Suppressed.untilTimeLimit;
import static org.apache.kafka.streams.kstream.Suppressed.untilWindowCloses;

// we will learn on how to use Windowing.
//@Configuration
public class InventoryFiveStream {

    @Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        inventorySerde.deserializer().setUseTypeHeaders(false);
        var longSerde = Serdes.Long();

        /*
         * Windowing operation will create Windowed class as key, for which we need serde from
         *  WindowedSerde class We define
         * one hour window serde like this
         * */
       // var windowLength = Duration.ofHours(1);
        var windowLength = Duration.ofMinutes(5);
        var windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());
        var inventoryTimestampExtractor = new InventoryTimestampExtractor();






        var inventoryStream = builder.stream("t.commodity.inventory",
                Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null));

        /* State Store */
       /* var storeName ="myStoreName";
        var storeBuilder = Stores.windowStoreBuilder(
                //  Stores.inMemoryWindowStore(storeName, Duration.ofMinutes(10), Duration.ofMinutes(1), false),
                Stores.persistentWindowStore(storeName, Duration.ofMinutes(10), Duration.ofMinutes(1),  false),
                Serdes.String(),
                Serdes.Long()
        );
        builder.addStateStore(storeBuilder);*/
        /*  */
        //Then we map the values, And group it, this time using one hour window,
        inventoryStream
               .filter((k,v) -> !v.equals("ADD"), Named.as("filter_out_invalid_Type"))
                .mapValues((v) -> v.getType().equalsIgnoreCase("ADD") ? v.getQuantity() : -1 * v.getQuantity(),
                        Named.as("Map_values"))

                .groupByKey()

                .windowedBy(TimeWindows.ofSizeWithNoGrace(windowLength))

               //.count()

               .reduce(Long::sum, Materialized.with(stringSerde, longSerde))

                .toStream()
                .through("t.commodity.inventory-total-five", Produced.with(windowSerde, longSerde))
                .print(Printed.toSysOut());

        return inventoryStream;
    }

}
