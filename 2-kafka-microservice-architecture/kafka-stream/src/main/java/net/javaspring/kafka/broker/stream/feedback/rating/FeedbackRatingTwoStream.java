package net.javaspring.kafka.broker.stream.feedback.rating;

import net.javaspring.kafka.broker.message.FeedbackMessage;
import net.javaspring.kafka.broker.message.FeedbackRatingOneMessage;
import net.javaspring.kafka.broker.message.FeedbackRatingTwoMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class FeedbackRatingTwoStream {

    @Bean
    public KStream<String, FeedbackMessage> kStreamFeedbackRating(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        feedbackSerde.deserializer().setUseTypeHeaders(false);

        var feedbackRatingTwoSerde = new JsonSerde<>(FeedbackRatingTwoMessage.class);
        feedbackRatingTwoSerde.deserializer().setUseTypeHeaders(false);

        var feedbackRatingTwoStoreValueSerde = new JsonSerde<>(FeedbackRatingTwoStoreValue.class);
        feedbackRatingTwoStoreValueSerde.deserializer().setUseTypeHeaders(false);

        var feedbackStream = builder.stream("t.commodity.feedback", Consumed.with(stringSerde, feedbackSerde));

        // to use state store, first we need the state store name
        var feedbackRatingStateStoreName = "feedbackRatingTwoStateStore";
        // use state store in memory
        var storeSupplier = Stores.inMemoryKeyValueStore(feedbackRatingStateStoreName);
        // add this state store to StreamsBuilder
        var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, stringSerde, feedbackRatingTwoStoreValueSerde);
        builder.addStateStore(storeBuilder);
        // use transformValues, using Feedback rating transformer
        // We need to pass the value transformer, using state store name, since the value transformer later will retrieve state from there.
        // The second parameter also state store name to be registered to kafka.
        feedbackStream.transformValues(() -> new FeedbackRatingTwoTransformer(feedbackRatingStateStoreName), feedbackRatingStateStoreName)
                .to("t.commodity.feedback.rating-two",
                        Produced.with(stringSerde, feedbackRatingTwoSerde));
        return feedbackStream;

    }
}
