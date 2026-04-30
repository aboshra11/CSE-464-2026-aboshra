package edu.asu.cse464;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class DFSSearch extends GraphSearchTemplate {

    private LinkedList<Node> stack;
    private Set<Node> visited;
    private Map<Node, Node> parentMap;

    @Override
    protected void initialize(Node src) {
        stack = new LinkedList<>();
        visited = new LinkedHashSet<>();
        parentMap = new LinkedHashMap<>();
        stack.push(src);
        parentMap.put(src, null);
    }

    @Override
    protected boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    protected Node getNext() {
        Node node = stack.pop();
        while (visited.contains(node) && !stack.isEmpty()) {
            node = stack.pop();
        }
        visited.add(node);
        return node;
    }

    @Override
    protected boolean isVisited(Node node) {
        return visited.contains(node);
    }

    @Override
    protected void addToFrontier(Node node, Node parent) {
        if (!parentMap.containsKey(node)) {
            parentMap.put(node, parent);
        }
        stack.push(node);
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

            // Add neighbors in reverse alphabetical order so stack pops in alphabetical order
            java.util.List<Node> neighbors = getSortedNeighbors(current);
            java.util.Collections.reverse(neighbors);
            for (Node neighbor : neighbors) {
                if (!isVisited(neighbor)) {
                    addToFrontier(neighbor, current);
                }
            }
        }

        return null;
    }
}