package net.javaspring.kafkaconsumer.error.handling;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service(value = "myFoodOrderErrorHandler")
@Slf4j
public class FoodOrderErrorHandler implements ConsumerAwareListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        log.warn("Food order error. Pretending to send to elasticsearch : {}, because : {}",
                message.getPayload(), exception.getMessage());
        // to re-throw exception to global error handler for FoodOrderErrorHandler
        if (exception.getCause() instanceof RuntimeException) {
            throw exception;
        }
        return null;
    }
}
