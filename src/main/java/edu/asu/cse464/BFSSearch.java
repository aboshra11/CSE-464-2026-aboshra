package edu.asu.cse464;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFSSearch extends GraphSearchTemplate {

    private Queue<Node> queue;
    private Map<Node, Node> parentMap;

    @Override
    protected void initialize(Node src) {
        queue = new LinkedList<>();
        parentMap = new LinkedHashMap<>();
        queue.add(src);
        parentMap.put(src, null);
    }

    @Override
    protected boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    protected Node getNext() {
        return queue.poll();
    }

    @Override
    protected boolean isVisited(Node node) {
        return parentMap.containsKey(node);
    }

    @Override
    protected void addToFrontier(Node node, Node parent) {
        parentMap.put(node, parent);
        queue.add(node);
    }

    @Override
    protected Node getParent(Node node) {
        return parentMap.get(node);
    }
}