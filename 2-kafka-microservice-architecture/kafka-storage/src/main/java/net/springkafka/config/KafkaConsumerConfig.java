package net.springkafka.config;


import net.springkafka.broker.message.DiscountMessage;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class KafkaConsumerConfig {


    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        DefaultKafkaConsumerFactory<String, DiscountMessage> cf = new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), new JsonDeserializer<>(DiscountMessage.class, false));

        return props;
    }




    @Bean
    public ConsumerFactory<String, DiscountMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),  new StringDeserializer(),
                new JsonDeserializer<>(DiscountMessage.class,false));
    }

    @Bean(value = "kafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, DiscountMessage>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DiscountMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
}
