package net.javaspring.kafka.broker.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebLayoutVoteMessage {

    private String  username;
    private String  layout;
}
