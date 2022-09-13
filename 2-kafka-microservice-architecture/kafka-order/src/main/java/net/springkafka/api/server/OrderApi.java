package net.springkafka.api.server;

import net.springkafka.api.request.OrderRequest;
import net.springkafka.api.response.OrderResponse;
import net.springkafka.command.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        // 1. save order using service
        String orderNumber  = orderService.saveOrder(orderRequest);

        // 2. create response
        OrderResponse orderResponse = new OrderResponse(orderNumber);

        // 3. return response
        return ResponseEntity.ok().body(orderResponse);
    }
}
