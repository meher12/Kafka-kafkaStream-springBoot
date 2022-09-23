package net.springkafka.command.service;

import net.springkafka.api.request.FeedbackRequest;
import net.springkafka.api.request.InventoryRequest;
import net.springkafka.command.action.FeedbackAction;
import net.springkafka.command.action.InventoryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryAction inventoryAction;

    public void createInventory(InventoryRequest request){

        inventoryAction.publishToKafka(request);

    }
    public void subtractingInventory(InventoryRequest request){
        inventoryAction.publishToKafka(request);
    }
}
