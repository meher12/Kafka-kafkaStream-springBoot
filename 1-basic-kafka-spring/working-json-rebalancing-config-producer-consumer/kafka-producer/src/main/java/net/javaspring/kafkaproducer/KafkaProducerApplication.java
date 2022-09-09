package net.javaspring.kafkaproducer;

import net.javaspring.kafkaproducer.entity.Employee;
import net.javaspring.kafkaproducer.producer.EmployeeJsonProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {

    @Autowired
    private EmployeeJsonProducer employeeJsonProducer;

    public static void main(String[] args) {

        SpringApplication.run(KafkaProducerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.now();
            var employee = new Employee("emp-" + i, "Employee" + i, dtf.format(localDate));
            employeeJsonProducer.sendMessage(employee);
        }
    }
}
