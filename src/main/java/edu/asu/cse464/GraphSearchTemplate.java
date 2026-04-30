package edu.asu.cse464;

import java.util.List;

public abstract class GraphSearchTemplate implements GraphSearchStrategy {

    // Template method — defines the skeleton of the algorithm
    public Path search(Node src, Node dst) {
        if (src == null || dst == null) return null;
        initialize(src);

        Path currentPath = new Path();
        currentPath.addNode(src);
        System.out.println("Visit Node History: " + currentPath.toHistoryString());

        while (hasNext()) {
            Node current = getNext();

            if (!current.equals(src)) {
                currentPath.addNode(current);
                System.out.println("Visit Node History: " + currentPath.toHistoryString());
            }

            if (current.equals(dst)) {
                System.out.println("Found target node: " + current.getLabel());
                return currentPath;
            }

            List<Node> neighbors = getSortedNeighbors(current);
            for (Node neighbor : neighbors) {
                if (!isVisited(neighbor)) {
                    addToFrontier(neighbor, current);
                }
            }
        }

        return null;
    }

    protected List<Node> getSortedNeighbors(Node node) {
        return graphEdges.stream()
                .filter(e -> e.getSource().equals(node))
                .map(Edge::getDestination)
                .sorted((a, b) -> a.getLabel().compareTo(b.getLabel()))
                .collect(java.util.stream.Collectors.toList());
    }

    protected List<Node> getNeighbors(Node node) {
        return graphEdges.stream()
                .filter(e -> e.getSource().equals(node))
                .map(Edge::getDestination)
                .toList();
    }

    // Common method — build path from src to current node
    protected Path buildCurrentPath(Node current) {
        Path path = new Path();
        Node step = current;
        java.util.LinkedList<Node> result = new java.util.LinkedList<>();
        while (step != null) {
            result.addFirst(step);
            step = getParent(step);
        }
        for (Node n : result) path.addNode(n);
        return path;
    }

    // Must be set before search
    protected java.util.Set<Edge> graphEdges;

    public void setEdges(java.util.Set<Edge> edges) {
        this.graphEdges = edges;
    }

    // Abstract methods — subclasses implement the differences
    protected abstract void initialize(Node src);
    protected abstract boolean hasNext();
    protected abstract Node getNext();
    protected abstract boolean isVisited(Node node);
    protected abstract void addToFrontier(Node node, Node parent);
    protected abstract Node getParent(Node node);
}