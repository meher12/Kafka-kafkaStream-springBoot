package net.springkafka.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {

    private String branchLocation;
    private LocalDateTime feedbackDateTime = LocalDateTime.now();
    private  int rating ;
    private String feedback;

}
