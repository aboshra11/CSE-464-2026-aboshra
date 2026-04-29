package edu.asu.cse464;

import java.util.List;

public abstract class GraphSearchTemplate {

    // Template method — defines the skeleton of the algorithm
    public final Path search(Node src, Node dst) {
        initialize(src);

        while (hasNext()) {
            Node current = getNext();
            System.out.println("visiting " + buildCurrentPath(current));

            if (current.equals(dst)) {
                return buildCurrentPath(current);
            }

            for (Node neighbor : getNeighbors(current)) {
                if (!isVisited(neighbor)) {
                    addToFrontier(neighbor, current);
                }
            }
        }

        return null;
    }

    // Common method — get neighbors from graph edges
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