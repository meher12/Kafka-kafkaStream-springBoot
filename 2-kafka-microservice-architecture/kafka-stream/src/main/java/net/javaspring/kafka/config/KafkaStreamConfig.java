package net.javaspring.kafka.config;

import net.javaspring.kafka.util.OnlineOrderTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfig() {
        var props = new HashMap<String, Object>();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-stream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        //Delay on table
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "3000");
        // Kafka streams deserialization error handling:
        // Config to log the error & continue processing
        props.put("default.deserialization.exception.handler", LogAndContinueExceptionHandler.class);
        // Config to log the error & stop processing
        // props.put("default.deserialization.exception.handler", LogAndFailExceptionHandler.class);

       // props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, OnlineOrderTimestampExtractor.class);

        return new KafkaStreamsConfiguration(props);

    }
}
