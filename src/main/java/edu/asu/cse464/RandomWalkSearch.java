package edu.asu.cse464;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class RandomWalkSearch extends GraphSearchTemplate {

    private final Random random = new Random();
    private Map<Node, Node> parentMap;
    private Set<Node> visited;

    @Override
    protected void initialize(Node src) {
        parentMap = new LinkedHashMap<>();
        visited = new LinkedHashSet<>();
        parentMap.put(src, null);
        visited.add(src);
    }

    @Override
    protected boolean hasNext() {
        return true; // controlled inside search()
    }

    @Override
    protected Node getNext() {
        return null; // not used
    }

    @Override
    protected boolean isVisited(Node node) {
        return visited.contains(node);
    }

    @Override
    protected void addToFrontier(Node node, Node parent) {
        // not used in random walk
    }

    @Override
    protected Node getParent(Node node) {
        return parentMap.get(node);
    }

    @Override
    public Path search(Node src, Node dst) {
        if (src == null || dst == null) return null;
        initialize(src);

        Node current = src;
        System.out.println("Visit Node History: " + src.getLabel());

        while (true) {
            if (current.equals(dst)) {
                System.out.println("Found target node: " + current.getLabel());
                return buildCurrentPath(current);
            }

            // Get unvisited neighbors only
            List<Node> unvisitedNeighbors = getNeighbors(current).stream()
                    .filter(n -> !visited.contains(n))
                    .collect(Collectors.toList());

            if (unvisitedNeighbors.isEmpty()) {
                System.out.println("Reached dead end at " + current.getLabel());
                return null;
            }

            // Pick a random unvisited neighbor
            Node next = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));
            parentMap.put(next, current);
            visited.add(next);
            current = next;

            Path currentPath = buildCurrentPath(current);
            System.out.println("Visit Node History: " + currentPath.toHistoryString());
        }
    }
}