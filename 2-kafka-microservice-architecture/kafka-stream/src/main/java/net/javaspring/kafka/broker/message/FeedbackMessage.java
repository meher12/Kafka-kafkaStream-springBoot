package net.javaspring.kafka.broker.message;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaspring.kafka.util.LocalDateTimeDeserializer;
import net.javaspring.kafka.util.LocalDateTimeSerializer;



import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackMessage {

    private String branchLocation;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime feedbackDateTime = LocalDateTime.now();
    private  int rating ;
    private String feedback;




}
