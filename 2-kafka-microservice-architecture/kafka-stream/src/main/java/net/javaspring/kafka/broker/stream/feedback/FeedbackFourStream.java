package net.javaspring.kafka.broker.stream.feedback;

import net.javaspring.kafka.broker.message.FeedbackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Configuration
public class FeedbackFourStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");
    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");

    @Bean
    public KStream<String, FeedbackMessage> kStreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        feedbackSerde.deserializer().setUseTypeHeaders(false);

        var sourceStream = builder.stream("t.commodity.feedback",
                Consumed.with(stringSerde, feedbackSerde));

        var feedbackStreams = sourceStream.flatMap(splitWords()).branch(isGoodWord(), isBadWord());

        feedbackStreams[0].to("t.commodity.feedback-four-good");
        feedbackStreams[0].groupByKey().count().toStream().to("t.commodity.feedback-four-good-count");

        feedbackStreams[1].to("t.commodity.feedback-four-bad");
        feedbackStreams[1].groupByKey().count().toStream().to("t.commodity.feedback-four-bad-count");


        return sourceStream;
    }

    private Predicate<? super String, ? super String> isGoodWord() {
        return (key, value) -> GOOD_WORDS.contains(value);
    }

    private Predicate<? super String, ? super String> isBadWord() {
        return (key, value) -> BAD_WORDS.contains(value);
    }



    private KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String,String>>> splitWords() {
        return (key, value) -> Arrays.asList(value.getFeedback()
                        .replaceAll("^a-zA-Z", "")
                        .toLowerCase().split("\\s+")).stream()
                .distinct().map(word -> KeyValue.pair(value.getBranchLocation(), word))
                .collect(toList());
    }

}
