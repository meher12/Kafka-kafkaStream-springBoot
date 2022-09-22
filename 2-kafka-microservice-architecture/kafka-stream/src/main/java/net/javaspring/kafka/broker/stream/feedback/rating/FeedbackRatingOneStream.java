package net.javaspring.kafka.broker.stream.feedback.rating;

import net.javaspring.kafka.broker.message.FeedbackMessage;
import net.javaspring.kafka.broker.message.FeedbackRatingOneMessage;
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
public class FeedbackRatingOneStream {

    @Bean
    public KStream<String, FeedbackMessage> kStreamFeedbackRating(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        feedbackSerde.deserializer().setUseTypeHeaders(false);

        var feedbackRatingOneSerde = new JsonSerde<>(FeedbackRatingOneMessage.class);
        feedbackRatingOneSerde.deserializer().setUseTypeHeaders(false);

        var feedbackRatingOneStoreValueSerde = new JsonSerde<>(FeedbackRatingOneStoreValue.class);
        feedbackRatingOneStoreValueSerde.deserializer().setUseTypeHeaders(false);

        var feedbackStream = builder.stream("t.commodity.feedback", Consumed.with(stringSerde, feedbackSerde));

        // to use state store, first we need the state store name
        var feedbackRatingStateStoreName = "feedbackRatingOneStateStore";
        // use state store in memory
        var storeSupplier = Stores.inMemoryKeyValueStore(feedbackRatingStateStoreName);
        // add this state store to StreamsBuilder
        var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, stringSerde, feedbackRatingOneStoreValueSerde);
        builder.addStateStore(storeBuilder);
        // use transformValues, using Feedback rating transformer
        // We need to pass the value transformer, using state store name, since the value transformer later will retrieve state from there.
        // The second parameter also state store name to be registered to kafka.
        feedbackStream.transformValues(() -> new FeedbackRatingOneTransformer(feedbackRatingStateStoreName), feedbackRatingStateStoreName)
                .to("t.commodity.feedback.rating-one",
                        Produced.with(stringSerde, feedbackRatingOneSerde));
        return feedbackStream;

    }
}
