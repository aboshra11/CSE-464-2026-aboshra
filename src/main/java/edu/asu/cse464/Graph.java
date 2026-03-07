package edu.asu.cse464;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<String, Node> nodes;
    private final Set<Edge> edges;

    public Graph() {
        nodes = new LinkedHashMap<>();
        edges = new LinkedHashSet<>();
    }

    public void addNode(String label) {
        if (label == null) {
            return;
        }

        label = label.trim();

        if (label.isEmpty()) {
            return;
        }

        if (!nodes.containsKey(label)) {
            nodes.put(label, new Node(label));
        }
    }

    public void addNodes(String[] labels) {
        if (labels == null) {
            return;
        }

        for (String label : labels) {
            addNode(label);
        }
    }

    public void addEdge(String srcLabel, String dstLabel) {
        if (srcLabel == null || dstLabel == null) {
            return;
        }

        srcLabel = srcLabel.trim();
        dstLabel = dstLabel.trim();

        if (srcLabel.isEmpty() || dstLabel.isEmpty()) {
            return;
        }

        addNode(srcLabel);
        addNode(dstLabel);

        Node src = nodes.get(srcLabel);
        Node dst = nodes.get(dstLabel);

        edges.add(new Edge(src, dst));
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void outputGraph(String filepath) throws IOException {
        Files.writeString(Paths.get(filepath), toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Number of nodes: ").append(nodes.size()).append("\n");
        sb.append("Node labels: ");
        for (Node node : nodes.values()) {
            sb.append(node.getLabel()).append(" ");
        }
        sb.append("\n");

        sb.append("Number of edges: ").append(edges.size()).append("\n");
        sb.append("Edges:\n");
        for (Edge edge : edges) {
            sb.append(edge).append("\n");
        }

        return sb.toString();
    }
}