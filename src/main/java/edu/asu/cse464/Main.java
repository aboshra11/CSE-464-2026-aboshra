package edu.asu.cse464;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = GraphParser.parseGraph("input.dot");

            graph.addNode("x");
            graph.addNode("a");

            String[] newNodes = {"y", "z", "x", "m"};
            graph.addNodes(newNodes);

            graph.addEdge("x", "y");
            graph.addEdge("y", "z");
            graph.addEdge("a", "b");
            graph.addEdge("m", "a");

            System.out.println(graph);

            graph.outputGraph("graph_output.txt");
            System.out.println("Graph output saved to graph_output.txt");

            graph.outputDOTGraph("exported_graph.dot");
            System.out.println("DOT graph saved to exported_graph.dot");

            graph.outputGraphics("exported_graph.png", "png");
            System.out.println("PNG graph saved to exported_graph.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}