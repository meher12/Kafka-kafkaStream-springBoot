package net.javaspring.kafka.broker.stream.feedback;

import net.javaspring.kafka.broker.message.FeedbackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;


import static java.util.stream.Collectors.toList;

@Configuration
public class FeedbackTwoStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    @Bean
    public KStream<String, String> kStreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        feedbackSerde.deserializer().setUseTypeHeaders(false);

        var goodFeedbackStream = builder.stream("t.commodity.feedback-one",
                Consumed.with(stringSerde, feedbackSerde))
                // Add branchLocation as the key and value "good word"
                .flatMap((k, v) ->  Arrays.asList(v.getFeedback()
                                .replaceAll("^a-zA-Z", "")
                                .toLowerCase().split("\\s+"))
                        .stream().filter(word -> GOOD_WORDS.contains(word))
                        .distinct()
                        .map(goodWord -> KeyValue.pair(v.getBranchLocation(), goodWord))
                        .collect(toList()));
        goodFeedbackStream.to("t.commodity.feedback-two-good");

        return goodFeedbackStream;
    }


}
