package net.javaspring.kafka.broker.stream.feedback.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRatingTwoStoreValue {

    private Map<Integer, Long> ratingMap = new TreeMap<>();
}
