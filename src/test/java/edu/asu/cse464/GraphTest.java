package edu.asu.cse464;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    // ─── Part 1 original tests ────────────────────────────────────────────────

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

    // ─── Scenario 1: nodes and edges correctly removed ───────────────────────

    @Test
    public void testRemoveNode() {
        Graph graph = new Graph();
        graph.addEdge("a", "b");
        graph.addEdge("b", "c");

        graph.removeNode("b"); // removes node b and both its edges

        assertFalse(graph.getNodes().containsKey("b"));
        assertEquals(2, graph.getNodes().size()); // a and c remain
        assertEquals(0, graph.getEdges().size()); // both edges removed
    }

    @Test
    public void testRemoveNodes() {
        Graph graph = new Graph();
        graph.addNodes(new String[]{"a", "b", "c", "d"});
        graph.addEdge("a", "b");

        graph.removeNodes(new String[]{"a", "b"});

        assertFalse(graph.getNodes().containsKey("a"));
        assertFalse(graph.getNodes().containsKey("b"));
        assertTrue(graph.getNodes().containsKey("c"));
        assertTrue(graph.getNodes().containsKey("d"));
        assertEquals(0, graph.getEdges().size());
    }

    @Test
    public void testRemoveEdge() {
        Graph graph = new Graph();
        graph.addEdge("a", "b");
        graph.addEdge("b", "c");

        graph.removeEdge("a", "b");

        assertEquals(1, graph.getEdges().size());   // only b->c remains
        assertTrue(graph.getNodes().containsKey("a")); // node a still exists
        assertTrue(graph.getNodes().containsKey("b")); // node b still exists
    }

    // ─── Scenario 2: removing non-existent node throws exception ─────────────

    @Test
    public void testRemoveNodeNotFound() {
        Graph graph = new Graph();
        graph.addNode("a");

        assertThrows(IllegalArgumentException.class, () -> graph.removeNode("z"));
    }

    @Test
    public void testRemoveNodesNotFound() {
        Graph graph = new Graph();
        graph.addNode("a");

        // "a" removes fine, "z" does not exist → exception
        assertThrows(IllegalArgumentException.class, () ->
                graph.removeNodes(new String[]{"a", "z"})
        );
    }

    // ─── Scenario 3: removing non-existent edge throws exception ─────────────

    @Test
    public void testRemoveEdgeNotFound() {
        Graph graph = new Graph();
        graph.addEdge("a", "b");

        // edge a->c does not exist
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("a", "c"));
    }

    @Test
    public void testRemoveEdgeNodeNotFound() {
        Graph graph = new Graph();
        graph.addNode("a");

        // node z doesn't exist at all
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("a", "z"));
    }
}