package distributed;

import java.util.*;
import java.util.concurrent.*;

public class Node extends Thread {
    private final int id;
    private boolean isCoordinator;
    private int acquiredResources = 0;
    private static final int MAX_RESOURCES = 10;
    private final BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
    private List<Node> nodes;

    public Node(int id) {
        this.id = id;
    }

    public int getNodeId() {
        return id;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void receiveMessage(Message msg) {
        inbox.add(msg);
    }

    public void setCoordinator(boolean coordinator) {
        this.isCoordinator = coordinator;
    }

    public boolean isCoordinator() {
        return isCoordinator;
    }

    private Node getCoordinator() {
        return nodes.stream().filter(Node::isCoordinator).findFirst().orElse(null);
    }

    private void startElection() {
        SimulationLogger.log("Node " + id + " starts election.");
        boolean higherExists = false;
        for (Node node : nodes) {
            if (node.getNodeId() > this.id && node.isAlive()) {
                higherExists = true;
                node.receiveMessage(new Message(MessageType.ELECTION, this.id));
            }
        }

        if (!higherExists) {
            this.isCoordinator = true;
            SimulationLogger.log("Node " + id + " becomes coordinator.");
            for (Node node : nodes) {
                if (node != this) {
                    node.receiveMessage(new Message(MessageType.COORDINATOR, this.id));
                }
            }
        }
    }

    @Override
    public void run() {
        while (acquiredResources < MAX_RESOURCES) {
            Node coordinator = getCoordinator();

            if (coordinator == null || !coordinator.isAlive()) {
                startElection();
                continue;
            }

            if (this != coordinator) {
                coordinator.receiveMessage(new Message(MessageType.REQUEST, this.id));
                boolean granted = false;

                try {
                    Message msg = inbox.poll(2, TimeUnit.SECONDS);
                    if (msg != null && msg.type == MessageType.OK) {
                        granted = true;
                    }
                } catch (InterruptedException ignored) {}

                if (!granted) {
                    SimulationLogger.log("Node " + id + " did not receive OK. Starting election.");
                    startElection();
                    continue;
                }

                SimulationLogger.log("Node " + id + " acquired resource " + (acquiredResources + 1));
                acquiredResources++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}

                coordinator.receiveMessage(new Message(MessageType.RELEASE, this.id));

            } else {
                try {
                    Message msg = inbox.poll(1, TimeUnit.SECONDS);
                    if (msg != null && msg.type == MessageType.REQUEST) {
                        Optional<Node> requester = nodes.stream()
                            .filter(n -> n.getNodeId() == msg.senderId)
                            .findFirst();
                        requester.ifPresent(n -> n.receiveMessage(new Message(MessageType.OK, this.id)));
                    }
                } catch (InterruptedException ignored) {}
            }
        }

        SimulationLogger.log("Node " + id + " completed all resource acquisitions.");
    }
}
