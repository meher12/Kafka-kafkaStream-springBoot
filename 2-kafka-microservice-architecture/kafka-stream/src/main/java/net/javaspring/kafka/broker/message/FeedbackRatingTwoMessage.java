package net.javaspring.kafka.broker.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRatingTwoMessage {

    private String location;
    private double averageRating;
    private Map<Integer, Long> ratingMap = new TreeMap<>();
}
