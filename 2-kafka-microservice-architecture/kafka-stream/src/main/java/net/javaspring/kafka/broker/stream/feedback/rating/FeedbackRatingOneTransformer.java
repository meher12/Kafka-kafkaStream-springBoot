package net.javaspring.kafka.broker.stream.feedback.rating;

import net.javaspring.kafka.broker.message.FeedbackMessage;
import net.javaspring.kafka.broker.message.FeedbackRatingOneMessage;
import org.apache.commons.lang3.StringUtils;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Optional;

public class FeedbackRatingOneTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingOneMessage> {

    private final String stateStoreName;
    // To access processor API
    private ProcessorContext processorContext;
    private KeyValueStore<String, FeedbackRatingOneStoreValue> ratingStateStore;

    public FeedbackRatingOneTransformer(String stateStoreName) {
        if (StringUtils.isEmpty(stateStoreName)) {
            throw new IllegalArgumentException("State store name must not empty");
        }
        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext context) {
        this.processorContext = context;
        this.ratingStateStore = (KeyValueStore<String,  FeedbackRatingOneStoreValue>) this.processorContext
                .getStateStore(stateStoreName);
    }

    @Override
    public FeedbackRatingOneMessage transform(FeedbackMessage feedbackMessage) {
       // if there is no data in state store for current location, we make new instance so the sum
      //  and count is zero.
        var storeValue = Optional.ofNullable(ratingStateStore.get(feedbackMessage.getBranchLocation()))
                .orElse(new FeedbackRatingOneStoreValue());

        // update new store (update the sum, adding current rating to existing sum)
        var newSumRating = storeValue.getSumRating() + feedbackMessage.getRating();
        storeValue.setSumRating(newSumRating);

        // The same way with count, we increment the count by one
        var newCountRating = storeValue.getCountRating() + 1;
        storeValue.setCountRating(newCountRating);

        // then put new store to state store (we update the state store value, using same key)
        ratingStateStore.put(feedbackMessage.getBranchLocation(), storeValue);

        // now we must do the transformation itself
        // build branch rating
        var branchRating = new FeedbackRatingOneMessage();
        branchRating.setLocation(feedbackMessage.getBranchLocation());
        double averageRating = Math.round((double) newSumRating / newCountRating * 10d) / 10d;
        branchRating.setAverageRating(averageRating);

        return branchRating;
    }

    @Override
    public void close() {

    }
}
