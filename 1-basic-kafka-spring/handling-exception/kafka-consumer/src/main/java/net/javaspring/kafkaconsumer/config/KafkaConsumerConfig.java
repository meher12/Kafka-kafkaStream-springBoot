package net.javaspring.kafkaconsumer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaconsumer.entity.SimpleNumber;
import net.javaspring.kafkaconsumer.error.handling.GlobalErrorHandler;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        var properties = kafkaProperties.buildConsumerProperties();

        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "120000"); // 2 minutes

        return new DefaultKafkaConsumerFactory<Object, Object>(properties);
    }

    // Custom Message Filter
    @Bean(name = "farLocationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());
        factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {

            final ObjectMapper objectMapper = new ObjectMapper();

            @Override
            public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {

                try {
                    var simpleNumber = objectMapper.readValue(consumerRecord.toString(), SimpleNumber.class);

                    return simpleNumber.getNumber() <= 3;


                } catch (JsonProcessingException e) {
                    //throw new RuntimeException(e);
                    return false;
                }
            }
        });
        return factory;
    }

    // register Global error handler in our kafka container factory
    @Bean(value = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());
        factory.setErrorHandler(new GlobalErrorHandler());
        return factory;
    }

    // create RetryTemplate
    private RetryTemplate createRetryTemplate() {

        var retryTemplate = new RetryTemplate();
        var retryPolicy = new SimpleRetryPolicy(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        var backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(10_000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }

    // retry consumer if failed
    @Bean(value = "imageRetryContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> imageRetryContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());

        factory.setErrorHandler(new GlobalErrorHandler());
        factory.setRetryTemplate(createRetryTemplate());
        return factory;
    }
}