package net.springkafka.api.server;

import net.springkafka.api.request.PremiumUser;
import net.springkafka.api.request.SubscriptionUser;
import net.springkafka.command.service.PremiumUserService;
import net.springkafka.command.service.SubscriptionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/subscriptionUser")
public class SubscriptionUserApi {

    @Autowired
    private SubscriptionUserService subscriptionUserService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody SubscriptionUser request) {
        subscriptionUserService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Username: "+request.getUsername() + "\n level: " + request.getDuration());
    }
}
