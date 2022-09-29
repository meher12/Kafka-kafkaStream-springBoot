package net.springkafka.api.server;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.PremiumUser;
import net.springkafka.command.service.DiscountService;
import net.springkafka.command.service.PremiumUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class PremiumUserApi {

    @Autowired
    private PremiumUserService premiumUserService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody PremiumUser request) {
        premiumUserService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Username: "+request.getUsername() + "\n level: " + request.getLevel());
    }
}
