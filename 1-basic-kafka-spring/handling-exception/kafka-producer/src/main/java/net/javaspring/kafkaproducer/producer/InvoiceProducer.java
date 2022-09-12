package net.javaspring.kafkaproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaspring.kafkaproducer.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendInvoice(Invoice invoice) throws JsonProcessingException {
        var jsonInvoice = objectMapper.writeValueAsString(invoice);
        kafkaTemplate.send("t_invoice", invoice.getNumber(), jsonInvoice);
    }
}
