// Cosa fa questa classe?
// Implementa Serializable per permettere l'invio del messaggio attraverso i socket.
// Contiene l'ID del nodo mittente e l'ID del messaggio.
// Ha un metodo toString() per stampare il messaggio in modo leggibile.

package utils;

import java.io.Serializable;

public class Message implements Serializable {
    private int nodeId;  // ID del nodo mittente
    private int messageId;  // ID del messaggio

    public Message(int nodeId, int messageId) {
        this.nodeId = nodeId;
        this.messageId = messageId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getMessageId() {
        return messageId;
    }

    @Override
    public String toString() {
        return "Message{Node ID=" + nodeId + ", Message ID=" + messageId + "}";
    }
}
