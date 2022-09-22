package net.springkafka.api.server;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.FlashSaleVoteRequest;
import net.springkafka.command.service.DiscountService;
import net.springkafka.command.service.FlashSaleVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/flashSale/vote")
public class FlashSaleVoteApi {

    @Autowired
    private FlashSaleVoteService flashSaleVoteService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody FlashSaleVoteRequest request) {
        flashSaleVoteService.createFlashSaleVote(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Customer code: "+request.getCustomerId() + "\n ItemName: " + request.getItemName());
    }
}
