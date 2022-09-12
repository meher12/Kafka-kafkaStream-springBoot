package net.javaspring.kafkaproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaspring.kafkaproducer.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper imageMapper = new ObjectMapper();

    public void sendImage(Image image) throws JsonProcessingException {

        String jsonImage = imageMapper.writeValueAsString(image);
        kafkaTemplate.send("t_image", image.getType(), jsonImage);
    }
}
