package net.javaspring.kafkaproducer;

import net.javaspring.kafkaproducer.api.CommodityApi;
import net.javaspring.kafkaproducer.sheduler.CommodityScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaProducerApplication implements CommandLineRunner {



    public static void main(String[] args) {

        SpringApplication.run(KafkaProducerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {



//        for (int i = 0; i < 5; i++) {
//
////            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////            LocalDate localDate = LocalDate.now();
////            dtf.format(localDate)
//            var employee = new Employee("emp-" + i, "Employee-" + i, LocalDate.now());
//            employeeJsonProducer.sendMessage(employee);
//        }
    }
}
