package net.javaspring.kafka.config;

import net.javaspring.kafka.util.OnlineOrderTimestampExtractor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.processor.To;
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

        /* we must use library that support exactly once, which is kafka stream.
        To enable exactly once
         */
       props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
       /*
        To enable idempotent producer, we need to add configuration on producer
        There are several ways to do this.
        We can configure through java code or spring
        application.yml.
        For example, configuration on kafka-stream
        project is java-code based, so open KafkaStreamsConfig,
        and add this line.
        */
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        return new KafkaStreamsConfiguration(props);

    }
}
