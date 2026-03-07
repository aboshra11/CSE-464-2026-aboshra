package edu.asu.cse464;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = GraphParser.parseGraph("input.dot");
            System.out.println(graph);

            graph.outputGraph("graph_output.txt");
            System.out.println("Graph output saved to graph_output.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}