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
public class InventoryThreeStream {

    @Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var longSerde = Serdes.Long();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        inventorySerde.deserializer().setUseTypeHeaders(false);

        var inventoryStream = builder.stream("t.commodity.inventory",
                Consumed.with(stringSerde, inventorySerde));

        /*
         Use reduce
        *
        * */
        inventoryStream.mapValues((key, value) -> value.getType()
                        .equalsIgnoreCase("ADD") ? value.getQuantity() : -1 * value.getQuantity())
                .groupByKey()
                .reduce(Long::sum, Materialized.with(stringSerde, longSerde))
                .toStream().to("t.commodity.inventory-total-three", Produced.with(stringSerde, longSerde));

        return inventoryStream;
    }

}
