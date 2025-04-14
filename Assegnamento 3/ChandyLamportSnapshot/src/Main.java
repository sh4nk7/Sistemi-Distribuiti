package src;

import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int NUM_NODES = 5;
        Map<Integer, BlockingQueue<Message>> queues = new HashMap<>();

        for (int i = 0; i < NUM_NODES; i++) {
            queues.put(i, new LinkedBlockingQueue<>());
        }

        Map<Integer, Node> nodes = new HashMap<>();
        for (int i = 0; i < NUM_NODES; i++) {
            Map<Integer, BlockingQueue<Message>> outboxes = new HashMap<>(queues);
            outboxes.remove(i);

            Set<Integer> incomingChannels = new HashSet<>(queues.keySet());
            incomingChannels.remove(i);

            Node node = new Node(i, outboxes, incomingChannels);
            nodes.put(i, node);
            new Thread(node).start();
        }

        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            int from = rand.nextInt(NUM_NODES);
            int to = rand.nextInt(NUM_NODES);
            if (from == to) continue;

            Message task = new Message(Message.Type.TASK, "TASK from " + from, from);
            queues.get(to).offer(task);
        }

        Thread.sleep(2000);
        System.out.println("\n=== Inizio snapshot da nodo 0 ===\n");
        nodes.get(0).startSnapshot();

        Thread.sleep(500);
        for (int i = 0; i < 30; i++) {
            int from = rand.nextInt(NUM_NODES);
            int to = rand.nextInt(NUM_NODES);
            if (from == to) continue;

            Message task = new Message(Message.Type.TASK, "POST-SNAPSHOT TASK from " + from, from);
            queues.get(to).offer(task);
        }

        Thread.sleep(4000);
        System.out.println("\n=== Stato snapshot ===\n");
        for (Node node : nodes.values()) {
            node.printSnapshotState();
            System.out.println();
        }
    }
}
