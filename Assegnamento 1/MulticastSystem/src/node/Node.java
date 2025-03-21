// Cosa fa questa classe?
// Invia messaggi multicast con ID incrementale fino a 100.
// Riceve messaggi multicast e li stampa.
// Gestisce la comunicazione con il MulticastManager.
// Utilizza i thread per inviare e ricevere contemporaneamente.

package node;

import utils.Message;
import utils.MulticastManager;
import java.io.IOException;

public class Node {
    private int nodeId;
    private int messageId = 0; // ID dell'ultimo messaggio inviato
    private MulticastManager multicastManager;

    public Node(int nodeId, String multicastAddress, int port) throws IOException {
        this.nodeId = nodeId;
        this.multicastManager = new MulticastManager(multicastAddress, port);
    }

    public void start() {
        new Thread(this::receiveMessages).start();
        sendMessages();
    }

    private void sendMessages() {
        while (messageId < 100) {
            messageId++;
            try {
                Message message = new Message(nodeId, messageId);
                multicastManager.sendMessage(message);
                System.out.println("🔹 Nodo " + nodeId + " ha inviato: " + message);
                Thread.sleep(1000); // Simula un intervallo tra gli invii
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("✅ Nodo " + nodeId + " ha completato l'invio.");
    }

    private void receiveMessages() {
        while (true) {
            try {
                Message message = multicastManager.receiveMessage();
                System.out.println("📩 Nodo " + nodeId + " ha ricevuto: " + message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("❌ Errore: Specificare l'ID del nodo.");
            return;
        }
        int nodeId = Integer.parseInt(args[0]);
        String multicastAddress = "230.0.0.0"; // Indirizzo multicast
        int port = 5000; // Porta di comunicazione

        try {
            Node node = new Node(nodeId, multicastAddress, port);
            node.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
