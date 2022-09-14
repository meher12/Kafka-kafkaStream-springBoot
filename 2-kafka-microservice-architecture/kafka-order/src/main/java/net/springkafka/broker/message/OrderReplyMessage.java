package net.springkafka.broker.message;

public class OrderReplyMessage {

    private String replyMessage;

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    @Override
    public String toString() {
        return "OrderReplyMessage{" +
                "replyMessage='" + replyMessage + '\'' +
                '}';
    }
}
