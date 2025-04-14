package src;

public class Message {
    public enum Type {
        TASK,
        MARKER
    }

    private final Type type;
    private final String content;
    private final int senderId;

    public Message(Type type, String content, int senderId) {
        this.type = type;
        this.content = content;
        this.senderId = senderId;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getSenderId() {
        return senderId;
    }

    @Override
    public String toString() {
        return "[" + type + "] from Node " + senderId + ": " + content;
    }
}
