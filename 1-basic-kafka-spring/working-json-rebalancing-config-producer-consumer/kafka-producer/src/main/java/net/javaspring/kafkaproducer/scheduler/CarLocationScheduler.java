package net.javaspring.kafkaproducer.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.javaspring.kafkaproducer.entity.CarLocation;
import net.javaspring.kafkaproducer.producer.CarLocationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CarLocationScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarLocationScheduler.class);

    private final CarLocation carOne;
    private final CarLocation carTwo;
    private final CarLocation carThree;

    @Autowired
    private CarLocationProducer carLocationProducer;

    public CarLocationScheduler() {
        var now = System.currentTimeMillis();

        carOne = new CarLocation("car-one", now, 0);
        carTwo = new CarLocation("car-two", now, 110);
        carThree = new CarLocation("car-three", now, 95);
    }

    @Scheduled(fixedRate = 10000)
    public void generateCarLocation() {
        var now = System.currentTimeMillis();

        carOne.setTimestamp(now);
        carTwo.setTimestamp(now);
        carThree.setTimestamp(now);

        carOne.setDistance(carOne.getDistance() + 1);
        carTwo.setDistance(carTwo.getDistance() - 1);
        carThree.setDistance(carThree.getDistance() + 1);

        try {
            carLocationProducer.send(carOne);
            LOGGER.info("Sent: {}", carOne);

            carLocationProducer.send(carTwo);
            LOGGER.info("Sent: {}", carTwo);

            carLocationProducer.send(carThree);
            LOGGER.info("Sent: {}", carThree);

        } catch (JsonProcessingException jsonProcessingException) {
            LOGGER.info("Error happened: {}", jsonProcessingException);
        }
    }
}
