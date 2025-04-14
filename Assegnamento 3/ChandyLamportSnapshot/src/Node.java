package src;

import java.util.*;
import java.util.concurrent.*;

public class Node implements Runnable {
    private final int id;
    private final BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
    private final Map<Integer, BlockingQueue<Message>> outboxes;
    private final Set<Integer> incomingChannels;

    private boolean recording = false;
    private final Set<Integer> markerReceivedFrom = new HashSet<>();
    private final List<Message> channelState = new ArrayList<>();
    private String localState = "Initial";

    public Node(int id, Map<Integer, BlockingQueue<Message>> outboxes, Set<Integer> incomingChannels) {
        this.id = id;
        this.outboxes = outboxes;
        this.incomingChannels = incomingChannels;
    }

    public BlockingQueue<Message> getInbox() {
        return inbox;
    }

    public void sendMessage(int targetId, Message message) {
        BlockingQueue<Message> queue = outboxes.get(targetId);
        if (queue != null) {
            queue.offer(message);
        }
    }

    public void startSnapshot() {
        if (!recording) {
            System.out.println("Node " + id + " starts snapshot.");
            recording = true;
            localState = "Snapshot taken at Node " + id;
            markerReceivedFrom.add(id);

            for (int neighborId : outboxes.keySet()) {
                sendMessage(neighborId, new Message(Message.Type.MARKER, "Marker", id));
            }
        }
    }

    private void finishSnapshotIfReady() {
        if (markerReceivedFrom.containsAll(incomingChannels)) {
            System.out.println("Node " + id + " has received MARKERS from all incoming channels.");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = inbox.take();

                if (message.getType() == Message.Type.TASK) {
                    System.out.println("Node " + id + " received TASK from Node " + message.getSenderId());
                    if (recording && !markerReceivedFrom.contains(message.getSenderId())) {
                        channelState.add(message);
                    }

                } else if (message.getType() == Message.Type.MARKER) {
                    if (!recording) {
                        System.out.println("Node " + id + " received first MARKER from Node " + message.getSenderId());
                        startSnapshot();
                    }
                    markerReceivedFrom.add(message.getSenderId());
                    finishSnapshotIfReady();
                }

                // Simula delay tra le operazioni
                Thread.sleep(30);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void printSnapshotState() {
        System.out.println("Node " + id + " local state: " + localState);
        System.out.println("Node " + id + " channel state (messages in transit):");
        for (Message m : channelState) {
            System.out.println("  " + m);
        }
    }
}
