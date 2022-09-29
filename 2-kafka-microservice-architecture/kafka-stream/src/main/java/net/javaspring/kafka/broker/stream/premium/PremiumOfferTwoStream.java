package net.javaspring.kafka.broker.stream.premium;

import net.javaspring.kafka.broker.message.PremiumOfferMessage;
import net.javaspring.kafka.broker.message.PremiumPurchaseMessage;
import net.javaspring.kafka.broker.message.PremiumUserMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.List;

@Configuration
public class PremiumOfferTwoStream {

    @Bean
    public KStream<String, PremiumOfferMessage> kStreamPremiumOffer(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        // to source stream
        var userSerde = new JsonSerde<>(PremiumUserMessage.class);
        userSerde.deserializer().setUseTypeHeaders(false);
        var purchaseSerde = new JsonSerde<>(PremiumPurchaseMessage.class);
        purchaseSerde.deserializer().setUseTypeHeaders(false);
        // to sink stream
        var offerSerde = new JsonSerde<>(PremiumOfferMessage.class);

        // stream(left/primary)

        var purchaseStream = builder.stream("t.commodity.premium-purchase",
                        Consumed.with(stringSerde, purchaseSerde))
                // Currently, purchase stream key is purchase number, so we need to change key to username.
                .selectKey((k, v) -> v.getUsername());

        //We only interested on gold and diamond level, so we need to filter only for those levels.
        var filterLevel = List.of("gold", "diamond");

        // table(right/secondary)
        var userTable = builder.table("t.commodity.premium-user",
                        Consumed.with(stringSerde, userSerde))
                .filter((key, value) -> filterLevel.contains(value.getLevel().toLowerCase()));

        // LeftJoin
        /*
        Joining stream and table will create another stream
        Create joiner method.
        This will takes premium purchase and premium user as input, and returns premium offer
        Send the join stream to sink topic
        * */
        var offerStream = purchaseStream.leftJoin(userTable, this::joiner,
                Joined.with(stringSerde, purchaseSerde,userSerde));
        offerStream.to("t.commodity.premium-offer-two", Produced.with(stringSerde, offerSerde));

        return offerStream;
    }

    private PremiumOfferMessage joiner(PremiumPurchaseMessage purchase, PremiumUserMessage user) {
        var result = new PremiumOfferMessage();

        result.setUsername(purchase.getUsername());
        result.setPurchaseNumber(purchase.getPurchaseNumber());
        if(user != null) {
        result.setLevel(user.getLevel());
        }



        return result;
    }
}
