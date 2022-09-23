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
public class InventoryOneStream {

    @Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var longSerde = Serdes.Long();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        inventorySerde.deserializer().setUseTypeHeaders(false);

        var inventoryStream = builder.stream("t.commodity.inventory",
                Consumed.with(stringSerde, inventorySerde));

        /*
        * We map the values to get quantity, then group by key Then we aggregate the KGroupedStream.
          The initializer for sum is 0, and the adder will be aggregate value plus new value.
        * */

        /*
        * The resulting record has item name for key, and total amount for value, so we need to
        * supply correct serde using Materialized class.
          Then we send the result to sink stream. Since aggregate produces KTable, we need to
          convert it to stream before send.
        *
        * */
        inventoryStream.mapValues((key, value) -> value.getQuantity()).groupByKey()
                .aggregate(() -> 0l, (aggKey, newValue, aggValue) -> aggValue + newValue, Materialized.with(stringSerde, longSerde))
                .toStream().to("t.commodity.inventory-total-one", Produced.with(stringSerde, longSerde));

        return inventoryStream;
    }

}
