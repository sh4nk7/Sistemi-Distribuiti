// Cosa fa questa classe?
// Avvia un server socket sulla porta 6000
// Attende che i nodi completino l'invio dei messaggi
// Chiude il sistema quando tutti i nodi hanno terminato



package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private static final int PORT = 6000;
    private Set<Integer> completedNodes = new HashSet<>();
    private int totalNodes;

    public Server(int totalNodes) {
        this.totalNodes = totalNodes;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("🌍 Server avviato sulla porta " + PORT);

            while (completedNodes.size() < totalNodes) {
                try (Socket socket = serverSocket.accept()) {
                    int nodeId = socket.getInputStream().read();
                    System.out.println("✅ Nodo " + nodeId + " ha completato.");

                    synchronized (completedNodes) {
                        completedNodes.add(nodeId);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("🎉 Tutti i nodi hanno completato! Il server chiude il sistema.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("❌ Errore: Specificare il numero totale di nodi.");
            return;
        }

        int totalNodes = Integer.parseInt(args[0]);
        Server server = new Server(totalNodes);
        server.start();
    }
}
