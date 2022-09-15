package net.javaspring.kafka.broker.serde;

import net.javaspring.kafka.broker.message.PromotionMessage;

import java.util.Map;

public class PromotionSerde extends  CustomJsonSerde<PromotionMessage>{



    public PromotionSerde() {
        super(new CustomJsonSerializer<PromotionMessage>(),
                new CustomJsonDeserializer<PromotionMessage>(PromotionMessage.class));
    }


}
