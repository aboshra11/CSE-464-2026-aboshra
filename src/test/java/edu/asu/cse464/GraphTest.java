package edu.asu.cse464;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void testParseGraph() throws IOException {
        String content = """
                digraph {
                a -> b;
                b -> c;
                c -> d;
                }
                """;

        Files.writeString(Paths.get("test_input.dot"), content);

        Graph graph = GraphParser.parseGraph("test_input.dot");

        assertEquals(4, graph.getNodes().size());
        assertEquals(3, graph.getEdges().size());
        assertTrue(graph.getNodes().containsKey("a"));
        assertTrue(graph.getNodes().containsKey("b"));
        assertTrue(graph.getNodes().containsKey("c"));
        assertTrue(graph.getNodes().containsKey("d"));
    }

    @Test
    public void testAddNode() {
        Graph graph = new Graph();

        graph.addNode("x");
        graph.addNode("x"); // duplicate

        assertEquals(1, graph.getNodes().size());
        assertTrue(graph.getNodes().containsKey("x"));
    }

    @Test
    public void testAddNodes() {
        Graph graph = new Graph();

        String[] labels = {"a", "b", "c", "a"};
        graph.addNodes(labels);

        assertEquals(3, graph.getNodes().size());
        assertTrue(graph.getNodes().containsKey("a"));
        assertTrue(graph.getNodes().containsKey("b"));
        assertTrue(graph.getNodes().containsKey("c"));
    }

    @Test
    public void testAddEdge() {
        Graph graph = new Graph();

        graph.addEdge("a", "b");
        graph.addEdge("a", "b"); // duplicate
        graph.addEdge("b", "c");

        assertEquals(3, graph.getNodes().size());
        assertEquals(2, graph.getEdges().size());
    }

    @Test
    public void testOutputDOTGraph() throws IOException {
        Graph graph = new Graph();
        graph.addEdge("a", "b");
        graph.addEdge("b", "c");

        graph.outputDOTGraph("test_output.dot");

        String output = Files.readString(Paths.get("test_output.dot"));

        String expected = """
                digraph {
                a -> b;
                b -> c;
                }
                """;

        assertEquals(expected, output);
    }

    @Test
    public void testOutputGraph() throws IOException {
        Graph graph = new Graph();
        graph.addEdge("a", "b");

        graph.outputGraph("test_graph_output.txt");

        String output = Files.readString(Paths.get("test_graph_output.txt"));

        assertTrue(output.contains("Number of nodes: 2"));
        assertTrue(output.contains("Number of edges: 1"));
        assertTrue(output.contains("a -> b"));
    }
}