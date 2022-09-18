package net.springkafka.api.server;

import net.springkafka.api.request.DiscountRequest;
import net.springkafka.api.request.FeedbackRequest;
import net.springkafka.command.service.DiscountService;
import net.springkafka.command.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/feedback")
public class FeedbackApi {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody FeedbackRequest request) {
        feedbackService.createFeedback(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Branch location: "+request.getBranchLocation()
                        + "\n DateTime feedback is: " + request.getFeedbackDateTime() +
                        "\n Rating is: " + request.getRating() +
                        "\n Feedback: "+request.getFeedback());
    }
}
