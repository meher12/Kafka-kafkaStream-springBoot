package net.javaspring.kafka.broker.stream.flashsale;

import net.javaspring.kafka.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.LocalDateTime;

import static net.javaspring.kafka.util.LocalDateTimeUtil.toEpochTimestamp;


public class FlashSaleVoteTwoValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {

    private final long voteStartTime;
    private final long voteEndTime;

    // to access processor API, we can use ProcessorContext class.
    private ProcessorContext processorContext;

    public FlashSaleVoteTwoValueTransformer(LocalDateTime voteStart, LocalDateTime voteEnd) {
        this.voteStartTime = toEpochTimestamp(voteStart);
        this.voteEndTime = toEpochTimestamp(voteEnd);
    }

    @Override
    public void init(ProcessorContext context) {
        //   This processor can be initialized on init() method.
        this.processorContext = context;
    }

    @Override
    public FlashSaleVoteMessage transform(FlashSaleVoteMessage value) {
        var recordTime = processorContext.timestamp();
        return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? value : null;
    }

    @Override
    public void close() {

    }
}
