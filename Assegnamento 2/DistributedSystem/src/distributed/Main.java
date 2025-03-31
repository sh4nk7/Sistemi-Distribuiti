package distributed;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            nodes.add(new Node(i));
        }

        for (Node node : nodes) {
            node.setNodes(nodes);
        }

        nodes.get(nodes.size() - 1).setCoordinator(true);

        for (Node node : nodes) {
            node.start();
        }
    }
}
