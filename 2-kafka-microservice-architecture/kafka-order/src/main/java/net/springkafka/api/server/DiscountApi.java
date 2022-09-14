package net.springkafka.api.server;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.command.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/discount")
public class DiscountApi {

    @Autowired
    private DiscountService discountService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody DiscountRequest request) {
        discountService.createDiscount(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Discount code: "+request.getDiscountCode() + "\n Percentage is: " + request.getDiscountPercentage() + "%");
    }
}
