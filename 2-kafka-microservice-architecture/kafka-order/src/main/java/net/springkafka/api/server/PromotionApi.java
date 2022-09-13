package net.springkafka.api.server;

import net.springkafka.api.request.PromotionRequest;
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
@RequestMapping("/api/promotion")
public class PromotionApi {

    @Autowired
    private PromotionService promotionService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody PromotionRequest request){
        promotionService.createPromotion(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(request.getPromotionCodeRq());
    }
}
