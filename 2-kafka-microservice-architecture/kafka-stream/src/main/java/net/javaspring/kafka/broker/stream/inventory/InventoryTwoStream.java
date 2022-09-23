package net.javaspring.kafka.broker.stream.inventory;

import net.javaspring.kafka.broker.message.InventoryMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class InventoryTwoStream {

    @Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var longSerde = Serdes.Long();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        inventorySerde.deserializer().setUseTypeHeaders(false);

        var inventoryStream = builder.stream("t.commodity.inventory",
                Consumed.with(stringSerde, inventorySerde));

        /*
        Aggregating KGroupedStream is always “adding”  using the adder parameter.
        We always add aggregated value with new value.
        So for subtracting inventory, we must convert the quantity on original record into negative value
        We can use field “type” on the original message.
        To get remove functionality, what we need to do is adjust the mapValues by using type,
        *
          Then we send the result to sink stream. Since aggregate produces KTable, we need to
          convert it to stream before send.
        *
        * */
        inventoryStream.mapValues((key, value) -> value.getType()
                        .equalsIgnoreCase("ADD") ? value.getQuantity() : -1 * value.getQuantity())
                .groupByKey()
                .aggregate(() -> 0l, (aggKey, newValue, aggValue) -> aggValue + newValue, Materialized.with(stringSerde, longSerde))
                .toStream().to("t.commodity.inventory-total-two", Produced.with(stringSerde, longSerde));

        return inventoryStream;
    }

}
