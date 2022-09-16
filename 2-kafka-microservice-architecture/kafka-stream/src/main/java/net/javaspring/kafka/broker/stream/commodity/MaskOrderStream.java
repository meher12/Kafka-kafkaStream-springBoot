package net.javaspring.kafka.broker.stream.commodity;

import net.javaspring.kafka.broker.message.OrderMessage;
import net.javaspring.kafka.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class MaskOrderStream {

    @Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);

        ((JsonDeserializer) orderSerde.deserializer()).setUseTypeHeaders(false);

        KStream<String, OrderMessage> maskOrderStream = builder.stream("t.commodity.order",
                Consumed.with(stringSerde, orderSerde)).mapValues(CommodityStreamUtil::maskCreditCard);

        maskOrderStream.to("t.commodity.order-masked", Produced.with(stringSerde, orderSerde));
        maskOrderStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Masked Order Stream"));

        return maskOrderStream;
    }
}
