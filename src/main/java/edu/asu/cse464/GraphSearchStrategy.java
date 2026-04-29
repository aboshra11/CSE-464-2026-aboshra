package edu.asu.cse464;

import java.util.Set;

public interface GraphSearchStrategy {
    /**
     * Must be called before search() to provide the graph edges.
     */
    void setEdges(Set<Edge> edges);

    /**
     * Searches for a path from src to dst.
     * Returns null if no path exists.
     */
    Path search(Node src, Node dst);
}