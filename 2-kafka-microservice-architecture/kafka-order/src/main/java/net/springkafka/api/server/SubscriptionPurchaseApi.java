package net.springkafka.api.server;

import net.springkafka.api.request.PremiumPurchase;
import net.springkafka.api.request.SubscriptionPurchase;
import net.springkafka.command.service.PremiumPurchaseService;
import net.springkafka.command.service.SubscriptionPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/subscriptionPurchase")
public class SubscriptionPurchaseApi {

    @Autowired
    private SubscriptionPurchaseService subscriptionPurchaseService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody SubscriptionPurchase request) {
        subscriptionPurchaseService.createPurchase(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Purchase number: "+request.getSubscriptionNumber());
    }
}
