package edu.asu.cse464;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalkSearch extends GraphSearchTemplate {

    private Node current;
    private final java.util.Map<Node, Node> parentMap = new java.util.LinkedHashMap<>();
    private final java.util.Set<Node> visited = new java.util.LinkedHashSet<>();
    private boolean done = false;
    private final Random random = new Random();

    @Override
    protected void initialize(Node src) {
        current = src;
        parentMap.put(src, null);
        visited.add(src);
    }

    @Override
    protected boolean hasNext() {
        return current != null && !done;
    }

    @Override
    protected Node getNext() {
        return current;
    }

    @Override
    protected boolean isVisited(Node node) {
        return visited.contains(node);
    }

    @Override
    protected void addToFrontier(Node node, Node parent) {
        // not used in random walk — next node chosen randomly
    }

    @Override
    protected Node getParent(Node node) {
        return parentMap.get(node);
    }

    @Override
    public Path search(Node src, Node dst) {
        initialize(src);

        while (hasNext()) {
            Node curr = getNext();
            System.out.println("visiting " + buildCurrentPath(curr));

            if (curr.equals(dst)) {
                return buildCurrentPath(curr);
            }

            List<Node> neighbors = getNeighbors(curr);
            if (neighbors.isEmpty()) {
                done = true;
                return null;
            }

            // Pick a random neighbor
            Node next = neighbors.get(random.nextInt(neighbors.size()));
            if (!parentMap.containsKey(next)) {
                parentMap.put(next, curr);
            }
            visited.add(next);
            current = next;
        }

        return null;
    }
}