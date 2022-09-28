package net.javaspring.kafka.broker.stream.webvote;

import net.javaspring.kafka.broker.message.WebColorVoteMessage;
import net.javaspring.kafka.broker.message.WebDesignVoteMessage;
import net.javaspring.kafka.broker.message.WebLayoutVoteMessage;
import net.javaspring.kafka.util.WebColorVoteTimestampExtractor;
import net.javaspring.kafka.util.WebLayoutVoteTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class WebDesignVoteToTableStream {

    @Bean
    public KStream<String, WebDesignVoteMessage> kStreamWebDesignVote(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var colorSerde = new JsonSerde<>(WebColorVoteMessage.class);
        var layoutSerde = new JsonSerde<>(WebLayoutVoteMessage.class);
        var designSerde = new JsonSerde<>(WebDesignVoteMessage.class);

        colorSerde.deserializer().setUseTypeHeaders(false);
        layoutSerde.deserializer().setUseTypeHeaders(false);
        designSerde.deserializer().setUseTypeHeaders(false);

        /* Color
        We will read from color stream and build table from it For this stream, we only need the color, so
        extract it using mapValues.
        The key is username, which is already set on vote producer.
         */
        var colorTable = builder.stream("t.commodity.web.vote-color", Consumed.with(stringSerde, colorSerde, new WebColorVoteTimestampExtractor(), null))
                .mapValues(v -> v.getColor()).toTable();


        /* Layout
        We will read from layout stream and build table from it For this stream, we only need the layout, so
        extract it using mapValues.
        The key is username, which is already set on vote producer.
         */
        var layoutTable = builder.stream("t.commodity.web.vote-layout", Consumed.with(stringSerde, layoutSerde, new WebLayoutVoteTimestampExtractor(), null))
                .mapValues(v -> v.getLayout()).toTable();


        // innerJoin
        var joinTable = colorTable.join(layoutTable, this::voteJoiner, Materialized.with(stringSerde, designSerde));
        joinTable.toStream().to("t.commodity.web.vote-four-result");

        /* vote Result
        to see the vote result. I will not send the vote result to kafka topic, only print it to console.
        The syntax is somewhat familiar with flash sale vote that we learned before This is for color vote
        */

        // this is for color vote
        joinTable.groupBy((username, voteDesign) -> KeyValue.pair(voteDesign.getColor(), voteDesign.getColor()))
                .count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote four - color"));
       // this is for layout vote
        joinTable.groupBy((username, voteDesign) -> KeyValue.pair(voteDesign.getLayout(), voteDesign.getLayout()))
                .count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote  four - layout "));

        return joinTable.toStream();
    }

    private WebDesignVoteMessage voteJoiner(String color, String layout) {
        var result = new WebDesignVoteMessage();
        result.setColor(color);
        result.setLayout(layout);

        return result;
    }
}
