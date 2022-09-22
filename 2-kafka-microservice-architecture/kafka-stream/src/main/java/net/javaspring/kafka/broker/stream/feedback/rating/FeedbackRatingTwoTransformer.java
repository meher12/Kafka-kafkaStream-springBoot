package net.javaspring.kafka.broker.stream.feedback.rating;

import net.javaspring.kafka.broker.message.FeedbackMessage;
import net.javaspring.kafka.broker.message.FeedbackRatingOneMessage;
import net.javaspring.kafka.broker.message.FeedbackRatingTwoMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Map;
import java.util.Optional;

public class FeedbackRatingTwoTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingTwoMessage> {

    // To access state store, we need state store name.
    private final String stateStoreName;
    // To access processor API
    private ProcessorContext processorContext;
    private KeyValueStore<String, FeedbackRatingTwoStoreValue> ratingStateStore;

    public FeedbackRatingTwoTransformer(String stateStoreName) {
        if (StringUtils.isEmpty(stateStoreName)) {
            throw new IllegalArgumentException("State store name must not empty");
        }
        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext context) {
        this.processorContext = context;
        // Then get the state store using the context and state store name
        this.ratingStateStore = (KeyValueStore<String, FeedbackRatingTwoStoreValue>) this.processorContext
                .getStateStore(stateStoreName);
    }

    @Override
    public FeedbackRatingTwoMessage transform(FeedbackMessage feedbackMessage) {
        // if there is no data in state store for current location, we make new instance so the sum
        //  and count is zero.
        var storeValue = Optional.ofNullable(ratingStateStore.get(feedbackMessage.getBranchLocation()))
                .orElse(new FeedbackRatingTwoStoreValue());

        var ratingMap = storeValue.getRatingMap();

        // Get the count for current rating
        var currentRatingCount = Optional.ofNullable(ratingMap.get(
                feedbackMessage.getRating())).orElse(0l);

        // Increment the count And put the update to state store
        var newRatingCount = currentRatingCount + 1;
        ratingMap.put(feedbackMessage.getRating(), newRatingCount);
        ratingStateStore.put(feedbackMessage.getBranchLocation(), storeValue);

        // build message to be sent to sink topic
        var branchRating = new FeedbackRatingTwoMessage();
        branchRating.setLocation(feedbackMessage.getBranchLocation());
        branchRating.setRatingMap(ratingMap);
        branchRating.setAverageRating(calculateAverage(ratingMap));

        return branchRating;
    }

    private double calculateAverage(Map<Integer, Long> ratingMap) {
        var sumRating = 0l;
        var countRating = 0;

        for (var entry : ratingMap.entrySet()) {
            sumRating += entry.getKey() * entry.getValue();
            countRating += entry.getValue();
        }
        return Math.round((double) sumRating / countRating * 10d) / 10d;
    }

    @Override
    public void close() {

    }
}
