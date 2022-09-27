package net.springkafka.api.server;

import net.springkafka.api.request.OnlineOrderRequest;
import net.springkafka.api.request.PromotionRequest;
import net.springkafka.command.service.OnlineOrderService;
import net.springkafka.command.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onlineOrder")
public class OnlineOrderApi {

    @Autowired
    private OnlineOrderService onlineOrderService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody OnlineOrderRequest request){
        onlineOrderService.createOnlineOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("online Order: " + request.getOnlineOrderNumber() + " " + request.getAmount() + " " + request.getUsername() + " "
        + request.getOnlineOrderDateTime());
    }
}
