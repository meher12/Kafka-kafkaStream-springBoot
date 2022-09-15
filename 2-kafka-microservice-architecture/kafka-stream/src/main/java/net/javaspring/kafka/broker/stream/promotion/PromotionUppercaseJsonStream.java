package net.javaspring.kafka.broker.stream.promotion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafka.broker.message.PromotionMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PromotionUppercaseJsonStream {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public KStream<String, String> kstreamPromotionUppercase(StreamsBuilder builder) {
        var stringSerde = Serdes.String();

        KStream<String, String> sourceStream = builder.stream("t.commodity.promotion",
                Consumed.with(stringSerde, stringSerde));
        KStream<String, String> uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);

        uppercaseStream.to("t.commodity.promotion-uppercase");

        sourceStream.print(Printed.<String, String>toSysOut().withLabel("JSON Original Stream"));
        uppercaseStream.print(Printed.<String, String>toSysOut().withLabel("JSON Uppercase Stream"));

        return sourceStream;
    }

    private String uppercasePromotionCode(String message) {
        try {
            var original = objectMapper.readValue(message, PromotionMessage.class);
            var converted = new PromotionMessage(original.getPromotionCode().toUpperCase());
            return objectMapper.writeValueAsString(converted);
        } catch (JsonProcessingException e) {
            log.warn("Can't process message {}", message);
        }
        return StringUtils.EMPTY;
    }
}
