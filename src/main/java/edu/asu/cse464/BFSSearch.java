package edu.asu.cse464;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BFSSearch extends GraphSearchTemplate {

    private Queue<Node> queue;
    private Map<Node, Node> parentMap;
    private Set<Node> visited;

    @Override
    protected void initialize(Node src) {
        queue = new LinkedList<>();
        parentMap = new LinkedHashMap<>();
        visited = new LinkedHashSet<>();
        queue.add(src);
        parentMap.put(src, null);
        visited.add(src);
    }

    @Override
    protected boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    protected Node getNext() {
        return queue.poll();
    }

    @Override
    protected boolean isVisited(Node node) {
        return visited.contains(node);
    }

    @Override
    protected void addToFrontier(Node node, Node parent) {
        visited.add(node);
        parentMap.put(node, parent);
        queue.add(node);
    }

    @Override
    protected Node getParent(Node node) {
        return parentMap.get(node);
    }

    @Override
    public Path search(Node src, Node dst) {
        if (src == null || dst == null) return null;
        initialize(src);

        while (hasNext()) {
            Node current = getNext();
            Path currentPath = buildCurrentPath(current);
            System.out.println("Visit Node History: " + currentPath.toHistoryString());

            if (current.equals(dst)) {
                System.out.println("Found target node: " + current.getLabel());
                return currentPath;
            }

            for (Node neighbor : getSortedNeighbors(current)) {
                if (!isVisited(neighbor)) {
                    addToFrontier(neighbor, current);
                }
            }
        }

        return null;
    }
}