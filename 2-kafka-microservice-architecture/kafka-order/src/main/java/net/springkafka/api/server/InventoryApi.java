package net.springkafka.api.server;

import net.springkafka.api.request.FeedbackRequest;
import net.springkafka.api.request.InventoryRequest;
import net.springkafka.command.service.FeedbackService;
import net.springkafka.command.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/inventory")
public class InventoryApi {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody InventoryRequest request) {
        inventoryService.createInventory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Inventory location: "+request.getLocation()
                        + "\n Item: " + request.getItem() +
                        "\n Quantity: " + request.getQuantity() +
                        "\n TransactionTime: " + request.getTransactionTime());
    }

    @PostMapping(value = "/subtract", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> subtract(@RequestBody InventoryRequest request) {
        inventoryService.subtractingInventory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Inventory location: "+request.getLocation()
                        + "\n Item: " + request.getItem() +
                        "\n Quantity: " + request.getQuantity() +
                        "\n Type: " + request.getType() +
                        "\n TransactionTime: " + request.getTransactionTime());
    }
}
