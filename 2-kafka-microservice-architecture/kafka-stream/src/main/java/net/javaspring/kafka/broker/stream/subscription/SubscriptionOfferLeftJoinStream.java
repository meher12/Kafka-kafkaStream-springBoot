package net.javaspring.kafka.broker.stream.subscription;

import net.javaspring.kafka.broker.message.SubscriptionOfferMessage;
import net.javaspring.kafka.broker.message.SubscriptionPurchaseMessage;
import net.javaspring.kafka.broker.message.SubscriptionUserMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class SubscriptionOfferLeftJoinStream {

    static final String USER_STORE = "subscription-user-store";

    @Bean
    public KStream<String, SubscriptionOfferMessage> kStreamSubscriptionOffer(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        // to source stream
        var userSerde = new JsonSerde<>(SubscriptionUserMessage.class);
        userSerde.deserializer().setUseTypeHeaders(false);
        var purchaseSerde = new JsonSerde<>(SubscriptionPurchaseMessage.class);
        purchaseSerde.deserializer().setUseTypeHeaders(false);
        // to sink stream
        var offerSerde = new JsonSerde<>(SubscriptionOfferMessage.class);
        offerSerde.deserializer().setUseTypeHeaders(false);

        // Stream left primary
        var purchaseStream = builder.stream("t.commodity.subscription-purchase",
                Consumed.with(stringSerde, purchaseSerde));

        // globalTable right secondary
        var userGlobalTable = builder.globalTable("t.commodity.subscription-user",
                Consumed.with(stringSerde, userSerde));

        var offerStream = purchaseStream.leftJoin(userGlobalTable,(key, value) -> key ,this::joiner);

        offerStream.to("t.commodity.subscription-offer-two", Produced.with(stringSerde, offerSerde));

        return offerStream;

    }

    private SubscriptionOfferMessage joiner(SubscriptionPurchaseMessage purchase, SubscriptionUserMessage user) {
        var result = new SubscriptionOfferMessage();

        result.setUsername(purchase.getUsername());
        result.setSubscriptionNumber(purchase.getSubscriptionNumber());
        if(user != null) {
            result.setDuration(user.getDuration());
        }
        return result;

    }
}
