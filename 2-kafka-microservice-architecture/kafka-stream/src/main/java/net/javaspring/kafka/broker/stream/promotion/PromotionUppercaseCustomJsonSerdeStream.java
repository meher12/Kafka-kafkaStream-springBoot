package net.javaspring.kafka.broker.stream.promotion;

import net.javaspring.kafka.broker.message.PromotionMessage;
import net.javaspring.kafka.broker.serde.PromotionSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class PromotionUppercaseCustomJsonSerdeStream {

    //Topology builder
    @Bean
    public KStream<String, PromotionMessage> kstreamPromotionUppercase(StreamsBuilder builder) {
        var stringSerde = Serdes.String(); // as key
        var jsonSerde = new PromotionSerde(); // as value


        KStream<String, PromotionMessage> sourceStream = builder.stream("t.commodity.promotion",
                Consumed.with(stringSerde, jsonSerde));
        KStream<String, PromotionMessage> uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCodeJ);

        uppercaseStream.to("t.commodity.promotion-uppercase", Produced.with(stringSerde, jsonSerde));

        sourceStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Custom Json Serde Original Stream"));
        uppercaseStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Custom Json Serde Uppercase Stream"));

        return sourceStream;
    }

    private PromotionMessage uppercasePromotionCodeJ(PromotionMessage message) {
        return new PromotionMessage(message.getPromotionCode().toUpperCase());
    }
}
