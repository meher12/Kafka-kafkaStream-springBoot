package net.javaspring.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaProducerApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerApplication.class);
//    @Autowired
//    private KafkaProducer producer;

    @Autowired
    private KafkaKeyProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        LOGGER.info("Timoti => {} ", Math.random());
//        producer.sendHello("Timotis "+ Math.random());

        for (int i = 0; i < 10000; i++) {
            var key = "key-" + (i % 4);

            var data = "data " + i + " with key " + key;
            producer.send(key, data);

            Thread.sleep(500);
        }
    }
}
