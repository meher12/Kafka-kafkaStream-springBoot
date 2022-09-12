package net.javaspring.kafkaproducer.service;

import net.javaspring.kafkaproducer.entity.Invoice;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class InvoiceService {

    private static int counter = 0;

    public Invoice generateInvoice(){
        counter++;
        var invoiceNumber = "INV-" +counter;
        var invoiceAmount = ThreadLocalRandom.current().nextInt(1, 1000);
        return new Invoice(invoiceNumber, invoiceAmount, "USD");
    }
}
