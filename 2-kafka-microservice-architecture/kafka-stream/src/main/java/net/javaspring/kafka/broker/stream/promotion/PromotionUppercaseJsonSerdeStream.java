package net.javaspring.kafka.broker.stream.promotion;

import net.javaspring.kafka.broker.message.PromotionMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonDeserializer;

//@Configuration
public class PromotionUppercaseJsonSerdeStream {

    //Topology builder
    @Bean
    public KStream<String, PromotionMessage> kstreamPromotionUppercase(StreamsBuilder builder) {
        var stringSerde = Serdes.String(); // as key
        var jsonSerde = new JsonSerde<>(PromotionMessage.class); // as value

        // because i create the serde by myself (new JsonSerde<>(PromotionMessage.class))
        // The property is ignored when i create my own serde
        //I'm responsible for configuring it (serde).
        //to tell the deserializer to ignore the type information in headers and use the provided fallback type
        // To correct this error net.javaspring.kafka.broker.message.PromotionMessage
        ((JsonDeserializer) jsonSerde.deserializer()).setUseTypeHeaders(false);

        KStream<String, PromotionMessage> sourceStream = builder.stream("t.commodity.promotion",
                Consumed.with(stringSerde, jsonSerde));
        KStream<String, PromotionMessage> uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);

        uppercaseStream.to("t.commodity.promotion-uppercase", Produced.with(stringSerde, jsonSerde));

        sourceStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel(" Json Serde Original Stream"));
        uppercaseStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Json Serde Uppercase Stream"));

        return sourceStream;
    }

    private PromotionMessage uppercasePromotionCode(PromotionMessage message) {
        return new PromotionMessage(message.getPromotionCode().toUpperCase());
    }
}
