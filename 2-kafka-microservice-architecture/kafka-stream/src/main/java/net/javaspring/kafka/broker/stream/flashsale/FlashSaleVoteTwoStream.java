package net.javaspring.kafka.broker.stream.flashsale;


import net.javaspring.kafka.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class FlashSaleVoteTwoStream {

    @Bean
    public KStream<String, String> kStreamFlashSaleVote(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);
        flashSaleVoteSerde.deserializer().setUseTypeHeaders(false);

        var voteStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 00));
        var voteEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 25));
        var flashSaleVoteStream = builder.stream("t.commodity.flashsale.vote",
                        Consumed.with(stringSerde, flashSaleVoteSerde))
                // for transformValues, using FlashSaleVoteTwoValueTransformer
                .transformValues(() -> new FlashSaleVoteTwoValueTransformer(voteStart, voteEnd))
                // take non null values only
                .filter((key, transformedValue) -> transformedValue != null)
                // get CustomerId as key and itemName as value
                .map((key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()));
        flashSaleVoteStream.to("t.commodity.flashsale.vote-user-item");

        // table
        builder.table("t.commodity.flashsale.vote-user-item",
                        Consumed.with(stringSerde, stringSerde))
                .groupBy((user, voteItem) -> KeyValue.pair(voteItem, voteItem))
                .count().toStream().to("t.commodity.flashsale.vote-two-result",
                        Produced.with(stringSerde, Serdes.Long()));

        return flashSaleVoteStream;

    }
}
