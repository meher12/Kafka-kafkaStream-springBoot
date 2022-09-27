package net.springkafka.api.server;

import net.springkafka.api.request.OnlineOrderRequest;
import net.springkafka.api.request.OnlinePaymentRequest;
import net.springkafka.command.service.OnlineOrderService;
import net.springkafka.command.service.OnlinePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onlinePayment")
public class OnlinePaymentApi {

    @Autowired
    private OnlinePaymentService onlinePaymentService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody OnlinePaymentRequest request){
        onlinePaymentService.createOnlinePayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Payment is :" + request.getPaymentNumber() + request.getPaymentMethod()
        + " " + request.getOnlinePaymentDateTime());
    }
}
