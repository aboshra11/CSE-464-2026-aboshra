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

    // ─── Add APIs ────────────────────────────────────────────────────────────

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

    // ─── Remove APIs ─────────────────────────────────────────────────────────

    public void removeNode(String label) {
        if (label == null || !nodes.containsKey(label.trim())) {
            throw new IllegalArgumentException("Node not found: " + label);
        }

        label = label.trim();
        Node toRemove = nodes.get(label);

        // Remove all edges connected to this node (as source or destination)
        edges.removeIf(e ->
                e.getSource().equals(toRemove) || e.getDestination().equals(toRemove)
        );

        nodes.remove(label);
    }

    public void removeNodes(String[] labels) {
        if (labels == null) {
            return;
        }

        for (String label : labels) {
            removeNode(label);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        if (srcLabel == null || dstLabel == null) {
            throw new IllegalArgumentException("Labels cannot be null.");
        }

        srcLabel = srcLabel.trim();
        dstLabel = dstLabel.trim();

        Node src = nodes.get(srcLabel);
        Node dst = nodes.get(dstLabel);

        if (src == null || dst == null) {
            throw new IllegalArgumentException("One or both nodes not found.");
        }

        Edge toRemove = new Edge(src, dst);
        if (!edges.contains(toRemove)) {
            throw new IllegalArgumentException("Edge not found: " + srcLabel + " -> " + dstLabel);
        }

        edges.remove(toRemove);
    }

    // ─── Getters ─────────────────────────────────────────────────────────────

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    // ─── Output APIs ─────────────────────────────────────────────────────────

    public void outputGraph(String filepath) throws IOException {
        Files.writeString(Paths.get(filepath), toString());
    }

    public void outputDOTGraph(String path) throws IOException {
        Files.writeString(Paths.get(path), toDOTString());
    }

    public void outputGraphics(String path, String format) throws IOException, InterruptedException {
        if (format == null || format.trim().isEmpty()) {
            throw new IllegalArgumentException("Format cannot be null or empty.");
        }

        format = format.trim().toLowerCase();

        if (!format.equals("png")) {
            throw new IllegalArgumentException("Only png format is currently supported.");
        }

        String tempDotFile = "temp_graph.dot";
        outputDOTGraph(tempDotFile);

        ProcessBuilder processBuilder = new ProcessBuilder(
                "dot",
                "-T" + format,
                tempDotFile,
                "-o",
                path
        );

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("Graphviz command failed with exit code: " + exitCode);
        }
    }

    // ─── String representations ───────────────────────────────────────────────

    public String toDOTString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");

        for (Edge edge : edges) {
            sb.append(edge.getSource().getLabel())
                    .append(" -> ")
                    .append(edge.getDestination().getLabel())
                    .append(";\n");
        }

        sb.append("}\n");
        return sb.toString();
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