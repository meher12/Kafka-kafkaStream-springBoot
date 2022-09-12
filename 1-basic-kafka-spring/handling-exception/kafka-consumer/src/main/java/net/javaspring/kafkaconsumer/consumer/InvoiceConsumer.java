package net.javaspring.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.javaspring.kafkaconsumer.entity.Invoice;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InvoiceConsumer {

    ObjectMapper invoiceMapper = new ObjectMapper();

    @KafkaListener(topics = "t_invoice", groupId = "cg-invoice", containerFactory = "invoiceDltContainerFactory")
    public void consumer(String message) throws JsonProcessingException {
        var invoiceReceived = invoiceMapper.readValue(message, Invoice.class);
        if (invoiceReceived.getAmount() < 1) {
            throw new IllegalArgumentException("Invoice amount : "
                    + invoiceReceived.getAmount() + "for invoice : " + invoiceReceived.getNumber());
        }
        log.info("Processing invoice : {}", invoiceReceived);
    }
}
