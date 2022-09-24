package net.javaspring.kafka.broker.stream.inventory;


import net.javaspring.kafka.broker.message.InventoryMessage;
import net.javaspring.kafka.util.InventoryTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

// we will learn on how to use custom timestamp extractor.
@Configuration
public class InventorySixStream {

    @Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var longSerde = Serdes.Long();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        inventorySerde.deserializer().setUseTypeHeaders(false);



 /*
         * Windowing operation will create Windowed class as key, for which we need serde from
         *  WindowedSerde class We define
         * one hour window serde like this
         * */
        var windowLength = Duration.ofHours(1);
        var hopLength = Duration.ofMinutes(20);
        var windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());



        var inventoryTimestampExtractor = new InventoryTimestampExtractor();

        var inventoryStream = builder.stream("t.commodity.inventory",
                Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null));

        //Then we map the values, And group it, this time using one hour window,
        inventoryStream
                .filter((k,v) -> !v.equals("ADD"), Named.as("filter_out_invalid_Type"))
                .mapValues((v) -> v.getType().equalsIgnoreCase("ADD") ? v.getQuantity() : -1 * v.getQuantity(),
                        Named.as("Map_values"))

                .groupByKey().windowedBy(TimeWindows.of(windowLength).advanceBy(hopLength))
                .reduce(Long::sum, Materialized.with(stringSerde, longSerde))
                .toStream()
                .through("t.commodity.inventory-total-six", Produced.with(windowSerde, longSerde))
                .print(Printed.toSysOut());

        return inventoryStream;
    }

}
