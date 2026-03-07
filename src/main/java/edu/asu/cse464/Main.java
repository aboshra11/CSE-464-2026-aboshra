package edu.asu.cse464;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = GraphParser.parseGraph("input.dot");

            graph.addNode("x");
            graph.addNode("a"); // duplicate node

            String[] newNodes = {"y", "z", "x", "m"};
            graph.addNodes(newNodes);

            graph.addEdge("x", "y");
            graph.addEdge("y", "z");
            graph.addEdge("a", "b"); // duplicate edge, should not be added again
            graph.addEdge("m", "a");

            System.out.println(graph);

            graph.outputGraph("graph_output.txt");
            System.out.println("Graph output saved to graph_output.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}