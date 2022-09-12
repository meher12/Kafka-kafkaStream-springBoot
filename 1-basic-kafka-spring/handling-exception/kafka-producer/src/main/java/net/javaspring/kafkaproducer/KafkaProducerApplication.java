package net.javaspring.kafkaproducer;

import net.javaspring.kafkaproducer.entity.FoodOrder;
import net.javaspring.kafkaproducer.entity.SimpleNumber;
import net.javaspring.kafkaproducer.producer.FoodOrderProducer;
import net.javaspring.kafkaproducer.producer.ImageProducer;
import net.javaspring.kafkaproducer.producer.InvoiceProducer;
import net.javaspring.kafkaproducer.producer.SimpleNumberProducer;
import net.javaspring.kafkaproducer.service.ImageService;
import net.javaspring.kafkaproducer.service.InvoiceService;
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

    @Autowired
    private ImageProducer imageProducer;

    @Autowired
    private ImageService imageService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceProducer invoiceProducer;

    public static void main(String[] args) {

        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // ----------------- KafkaListener Error Handler  -----------------
        FoodOrder chickenOrder = new FoodOrder(3, "Chicken");
        FoodOrder fishOrder = new FoodOrder(10, "Fish");
        FoodOrder pizzaOrder = new FoodOrder(5, "Pizza");

        producer.send(chickenOrder);
        producer.send(fishOrder);
        producer.send(pizzaOrder);
        
        // ----------------- GlobalError Handler  -----------------
        for (int i = 1; i < 4; i++) {
            var simpleNumber = new SimpleNumber(i);
            simpleNumberProducer.send(simpleNumber);
        }

        // ----------------- Retrying Consumer  -----------------
        var image1 = imageService.generateImage("jpg");
        var image2 = imageService.generateImage("svg");
        var image3 = imageService.generateImage("png");

        imageProducer.sendImage(image1);
        imageProducer.sendImage(image2);
        imageProducer.sendImage(image3);

        // ----------------- Dead Letter Queues  -----------------
        for (int i = 0; i < 10; i++) {
            var invoice  = invoiceService.generateInvoice();

            if (i >= 5) {
                invoice.setAmount(-1);
            }

            invoiceProducer.sendInvoice(invoice);
        }
        

    }

}
