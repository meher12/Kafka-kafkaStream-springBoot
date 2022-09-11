package net.javaspring.kafkaproducer;

import net.javaspring.kafkaproducer.entity.FoodOrder;
import net.javaspring.kafkaproducer.producer.FoodOrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Autowired
	private FoodOrderProducer producer;

	@Override
	public void run(String... args) throws Exception {
		FoodOrder chickenOrder = new FoodOrder(3, "Chicken");
		FoodOrder fishOrder = new FoodOrder(10, "Fish");
		FoodOrder pizzaOrder = new FoodOrder(5, "Pizza");

		producer.send(chickenOrder);
		producer.send(fishOrder);
		producer.send(pizzaOrder);
	}

}
