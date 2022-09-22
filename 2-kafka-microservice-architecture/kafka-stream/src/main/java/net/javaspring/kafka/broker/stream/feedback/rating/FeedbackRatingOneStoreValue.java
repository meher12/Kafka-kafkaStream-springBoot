package net.javaspring.kafka.broker.stream.feedback.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRatingOneStoreValue {

    private long countRating;
    private long sumRating;
}
