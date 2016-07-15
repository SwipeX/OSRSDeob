package pw.tdekk.deob.cfg;

import java.util.*;

/**
 * @author Tim Dekker
 *         Adapted from code by: Tyler Sedlar
 */
public class DirectedGraph<V, E> implements Iterable<V> {

    private final Map<V, List<E>> graph = new HashMap<>();

    @SuppressWarnings("unchecked")
    public Set<E> getEdgeAt(int index) {
        return (Set<E>) graph.values().toArray()[index];
    }

    public int getSize() {
        return graph.size();
    }

    public boolean containsVertex(V vertex) {
        return graph.containsKey(vertex);
    }

    public boolean containsEdge(V vertex, E edge) {
        return graph.containsKey(vertex) && graph.get(vertex).contains(edge);
    }

    public void removeVertex(V vertex) {
        graph.remove(vertex);
    }

    public void transfer(V vertex, V oldVertex) {
        List<E> edges = edgesFrom(oldVertex);
        edges.forEach(e -> addEdge(vertex, e));
    }

    public boolean addVertex(V vertex) {
        if (graph.containsKey(vertex)) {
            return false;
        }
        graph.put(vertex, new ArrayList<>());
        return true;
    }

    public void addEdge(V start, E dest) {
        if (!graph.containsKey(start)) {
            return;
        }
        graph.get(start).add(dest);
    }

    public void removeEdge(V start, E dest) {
        if (!graph.containsKey(start)) {
            return;
        }
        graph.get(start).remove(dest);
    }

    public List<E> edgesFrom(V node) {
        return graph.get(node);
    }

    public void graph(DirectedGraph<V, E> graph) {
        this.graph.putAll(graph.graph);
    }

    @Override
    public final Iterator<V> iterator() {
        return graph.keySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (V v : graph.keySet()) {
            builder.append("\n    ").append(v).append(" -> ").append(graph.get(v));
        }
        return builder.toString();
    }

    public void flush() {
        graph.clear();
    }
}