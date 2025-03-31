package distributed;

public class Message {
    public MessageType type;
    public int senderId;

    public Message(MessageType type, int senderId) {
        this.type = type;
        this.senderId = senderId;
    }
}
