package net.springkafka.api.server;


import net.springkafka.api.request.WebLayoutVote;
import net.springkafka.command.service.WebLayoutVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webLayout/vote")
public class WebLayoutVoteApi {

    @Autowired
    private WebLayoutVoteService webLayoutVoteService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody WebLayoutVote request){
        webLayoutVoteService.createWebLayout(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(request.getUsername() + " " + request.getLayout());
    }
}
