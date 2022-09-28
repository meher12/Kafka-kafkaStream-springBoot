package net.javaspring.kafka.broker.stream.orderpayment;

import net.javaspring.kafka.broker.message.OnlineOrderMessage;
import net.javaspring.kafka.broker.message.OnlineOrderPaymentMessage;
import net.javaspring.kafka.broker.message.OnlinePaymentMessage;
import net.javaspring.kafka.util.OnlineOrderTimestampExtractor;
import net.javaspring.kafka.util.OnlinePaymentTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;


//@Configuration
public class OrderPaymentOneStream {

    @Bean
    public KStream<String, OnlineOrderMessage> kStreamOrderPayment(StreamsBuilder builder) {
        var stringSerde = Serdes.String();

        var onlineOrderSerde = new JsonSerde<>(OnlineOrderMessage.class);
        var onlinePaymentSerde = new JsonSerde<>(OnlinePaymentMessage.class);
        var orderPaymentSerde = new JsonSerde<>(OnlineOrderPaymentMessage.class);

        onlineOrderSerde.deserializer().setUseTypeHeaders(false);
        onlinePaymentSerde.deserializer().setUseTypeHeaders(false);
        orderPaymentSerde.deserializer().setUseTypeHeaders(false);

        var orderStream = builder.stream("t.commodity.online-order",
                Consumed.with(stringSerde, onlineOrderSerde, new OnlineOrderTimestampExtractor(), null));

        var paymentStream = builder.stream("t.commodity.online-payment",
                Consumed.with(stringSerde, onlinePaymentSerde, new OnlinePaymentTimestampExtractor(), null));



        /*
        // innerJoin
        orderStream.join(paymentStream, this::joinOrderPayment,
                        JoinWindows.of(Duration.ofHours(1)),
                        StreamJoined.with(stringSerde, onlineOrderSerde, onlinePaymentSerde))
                .to("t.commodity.join-order-payment-one", Produced.with(stringSerde, orderPaymentSerde));*/

       /*
       // leftJoin
        orderStream.leftJoin(paymentStream, this::joinOrderPayment,
                        JoinWindows.of(Duration.ofHours(1)),
                        StreamJoined.with(stringSerde, onlineOrderSerde, onlinePaymentSerde))
                .to("t.commodity.join-order-payment-two", Produced.with(stringSerde, orderPaymentSerde));*/

        // outerJoin
        orderStream.leftJoin(paymentStream, this::joinOrderPayment,
                        JoinWindows.of(Duration.ofHours(1)),
                        StreamJoined.with(stringSerde, onlineOrderSerde, onlinePaymentSerde))
                .to("t.commodity.join-order-payment-three", Produced.with(stringSerde, orderPaymentSerde));

        return orderStream;
    }

    private OnlineOrderPaymentMessage joinOrderPayment(OnlineOrderMessage onlineOrder, OnlinePaymentMessage onlinePayment) {
        var result = new OnlineOrderPaymentMessage();

        // the condition if for outerJoin
        if(onlineOrder != null) {
            result.setOnlineOrderNumber(onlineOrder.getOnlineOrderNumber());
            result.setAmount(onlineOrder.getAmount());
            result.setUsername(onlineOrder.getUsername());
            result.setOnlineOrderDateTime(onlineOrder.getOnlineOrderDateTime());
        }
        // the condition if for leftJoin and outerJoin
        if (onlinePayment != null) {
            result.setPaymentNumber(onlinePayment.getPaymentNumber());
            result.setPaymentMethod(onlinePayment.getPaymentMethod());
            result.setOnlinePaymentDateTime(onlinePayment.getOnlinePaymentDateTime());
        }

        return result;

    }
}
