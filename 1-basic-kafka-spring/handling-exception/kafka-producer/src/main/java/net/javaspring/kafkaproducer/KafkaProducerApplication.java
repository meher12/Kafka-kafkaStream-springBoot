package net.javaspring.kafkaproducer;

import net.javaspring.kafkaproducer.entity.FoodOrder;
import net.javaspring.kafkaproducer.entity.SimpleNumber;
import net.javaspring.kafkaproducer.producer.FoodOrderProducer;
import net.javaspring.kafkaproducer.producer.SimpleNumberProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {

    @Autowired
    private FoodOrderProducer producer;
    @Autowired
    private SimpleNumberProducer simpleNumberProducer;

    public static void main(String[] args) {

        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        FoodOrder chickenOrder = new FoodOrder(3, "Chicken");
        FoodOrder fishOrder = new FoodOrder(10, "Fish");
        FoodOrder pizzaOrder = new FoodOrder(5, "Pizza");

        producer.send(chickenOrder);
        producer.send(fishOrder);
        producer.send(pizzaOrder);
        // -------------------------------------
        for (int i = 1; i < 4; i++) {
            var simpleNumber = new SimpleNumber(i);
            simpleNumberProducer.send(simpleNumber);
        }
    }

}
