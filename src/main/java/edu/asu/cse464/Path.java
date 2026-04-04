package edu.asu.cse464;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List<Node> nodes;

    public Path() {
        nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            sb.append(nodes.get(i).getLabel());
            if (i < nodes.size() - 1) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }
}