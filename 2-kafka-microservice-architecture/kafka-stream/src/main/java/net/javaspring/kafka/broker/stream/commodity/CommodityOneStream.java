package net.javaspring.kafka.broker.stream.commodity;


import net.javaspring.kafka.broker.message.OrderMessage;
import net.javaspring.kafka.broker.message.OrderPatternMessage;
import net.javaspring.kafka.broker.message.OrderRewardMessage;
import net.javaspring.kafka.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class CommodityOneStream {

    @Bean
    public KStream<String, OrderMessage> kStreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();

        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        ((JsonDeserializer) orderSerde.deserializer()).setUseTypeHeaders(false);
        ((JsonDeserializer) orderPatternSerde.deserializer()).setUseTypeHeaders(false);
        ((JsonDeserializer) orderRewardSerde.deserializer()).setUseTypeHeaders(false);

        // source stream to order
        KStream<String, OrderMessage> maskOrderStream = builder.stream("t.commodity.order",
                Consumed.with(stringSerde, orderSerde)).mapValues(CommodityStreamUtil::maskCreditCard);

        // 1st sink stream to pattern
        // summarize order item (total = price * quantity)
        KStream<String, OrderPatternMessage> patternStream = maskOrderStream.mapValues(CommodityStreamUtil::mapToOrderPattern);
        patternStream.to("t.commodity.pattern-one", Produced.with(stringSerde, orderPatternSerde));

        // 2nd sink stream to reward
        // filter only "large" quantity
        KStream<String, OrderRewardMessage> rewardStream = maskOrderStream
                .filter(CommodityStreamUtil.isLargeQuantity()).mapValues(CommodityStreamUtil::mapToOrderReward);
        rewardStream.to("t.commodity.reward-one", Produced.with(stringSerde, orderRewardSerde));

        // 3rd sink stream to storage
        // no transformation
        maskOrderStream.to("t.commodity.storage-one", Produced.with(stringSerde, orderSerde));

        return maskOrderStream;

    }
}
