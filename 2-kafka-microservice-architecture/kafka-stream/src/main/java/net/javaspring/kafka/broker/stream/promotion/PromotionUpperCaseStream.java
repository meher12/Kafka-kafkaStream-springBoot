package net.javaspring.kafka.broker.stream.promotion;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class PromotionUpperCaseStream {

    @Bean
    public KStream<String, String> kstreamPromotionUppercase(StreamsBuilder builder){
        // Kstream input topic: t.commodity.promotion
        // Kstream output topic: t.commodity.promotion-uppercase
        KStream<String, String> sourceStream = builder.stream("t.commodity.promotion",
                Consumed.with(Serdes.String(), Serdes.String()));
        KStream<String, String> uppercaseStream = sourceStream.mapValues(s -> s.toUpperCase());
        uppercaseStream.to("t.commodity.promotion-uppercase");

        sourceStream.print(Printed.<String, String>toSysOut().withLabel("Original Stream"));
        uppercaseStream.print(Printed.<String, String>toSysOut().withLabel("Uppercase Stream"));

        return sourceStream;
    }
}
