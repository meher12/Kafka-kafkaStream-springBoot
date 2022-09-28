package net.springkafka.api.server;

import net.springkafka.api.request.PromotionRequest;
import net.springkafka.api.request.WebColorVote;
import net.springkafka.command.service.PromotionService;
import net.springkafka.command.service.WebColorVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webColor/vote")
public class WebColorVoteApi {

    @Autowired
    private WebColorVoteService webColorVoteService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody WebColorVote request){
        webColorVoteService.createWebColor(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(request.getUsername() + " " + request.getColor());
    }
}
