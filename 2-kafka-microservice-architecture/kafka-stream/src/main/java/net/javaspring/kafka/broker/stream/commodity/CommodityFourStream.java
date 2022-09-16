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
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonSerde;

import static net.javaspring.kafka.util.CommodityStreamUtil.*;

@Configuration
public class CommodityFourStream {

    @Bean
    public KStream<String, OrderMessage> kStreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();

        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        orderSerde.deserializer().setUseTypeHeaders(false);
        orderPatternSerde.deserializer().setUseTypeHeaders(false);
        orderRewardSerde.deserializer().setUseTypeHeaders(false);

        // source stream to order
        KStream<String, OrderMessage> maskOrderStream = builder.stream("t.commodity.order",
                Consumed.with(stringSerde, orderSerde)).mapValues(CommodityStreamUtil::maskCreditCard);

        // 1st sink stream to pattern
        final var branchProducer = Produced.with(stringSerde, orderPatternSerde);
        new KafkaStreamBrancher<String, OrderPatternMessage>()
                .branch(isPlastic(), kStream -> kStream.to("t.commodity.pattern-four-plastic", branchProducer))
                .defaultBranch(kStream -> kStream.to("t.commodity.pattern-four-notplastic", branchProducer))
                .onTopOf(maskOrderStream.mapValues(CommodityStreamUtil::mapToOrderPattern));
        // 2nd sink stream to reward
        // filter only "large" quantity and not cheap
        KStream<String, OrderRewardMessage> rewardStream = maskOrderStream
                .filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(isCheap())
                .map(CommodityStreamUtil.mapToOrderRewardChangeKey());
        rewardStream.to("t.commodity.reward-four", Produced.with(stringSerde, orderRewardSerde));

        // 3rd sink stream to storage
        // generate base64 key and reply it
        KStream<String, OrderMessage> storageStream = maskOrderStream
                .selectKey(generateStorageKey());
        maskOrderStream.to("t.commodity.storage-four", Produced.with(stringSerde, orderSerde));

        return maskOrderStream;

    }
}
