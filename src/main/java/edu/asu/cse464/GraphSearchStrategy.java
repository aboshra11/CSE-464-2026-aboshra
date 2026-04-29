package edu.asu.cse464;

import java.util.Set;

public interface GraphSearchStrategy {
    void setEdges(Set<Edge> edges);
    Path search(Node src, Node dst);
}