package net.javaspring.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaconsumer.entity.Image;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.http.HttpConnectTimeoutException;

@Service
@Slf4j
public class ImageConsumer {

    ObjectMapper imageMapper  = new ObjectMapper();

    @KafkaListener(topics = "t_image", groupId = "cg-image", containerFactory = "imageRetryContainerFactory")
    public void consume(String message) throws JsonProcessingException, HttpConnectTimeoutException {
        var imageReceived = imageMapper.readValue(message, Image.class);

        if(imageReceived.getType().equalsIgnoreCase("svg")){
            throw new HttpConnectTimeoutException("Simulate failed API call");
        }
        log.info("Processing image : {}", imageReceived);
    }
}
