package Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:41 PM
 */
public abstract class Graph<T, N extends Node<T>, E extends Edge> {

    protected int nodecount = 0;
    protected Map<N, Integer> nodes;
    protected Set<E> edges;
    protected Map<N, Set<N>> succ, pred, adj;
    protected Map<N, Map<N, E>> findEdges;

    Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
        succ = new HashMap<>();
        pred = new HashMap<>();
        adj = new HashMap<>();
        findEdges = new HashMap<>();
    }

    public Set<N> getNodes() {
        return nodes.keySet();
    }

    public N newNode(T el) {
        N n = createNode(el);
        nodes.put(n, nodecount++);
        return n;
    }

    protected abstract N createNode(T el);

    // add an undirected edge to the graph
    public E newEdge(N n1, N n2) {
        E edge = createEdge(n1, n2);
        edges.add(edge);

        // setup sets for n1
        if (!succ.containsKey(n1)) succ.put(n1, new HashSet<N>());
        if (!pred.containsKey(n1)) pred.put(n1, new HashSet<N>());
        if (!adj.containsKey(n1)) adj.put(n1, new HashSet<N>());
        // setup sets for n2
        if (!succ.containsKey(n2)) succ.put(n2, new HashSet<N>());
        if (!pred.containsKey(n2)) pred.put(n2, new HashSet<N>());
        if (!adj.containsKey(n2)) adj.put(n2, new HashSet<N>());
        // add n2 to n1's sets
        succ.get(n1).add(n2);
        pred.get(n1).add(n2);
        adj.get(n1).add(n2);
        // add n1 to n2's sets
        succ.get(n2).add(n1);
        pred.get(n2).add(n1);
        adj.get(n2).add(n1);

        if (!findEdges.containsKey(n1)) findEdges.put(n1, new HashMap<N, E>());
        if (!findEdges.get(n1).containsKey(n2)) findEdges.get(n1).put(n2, edge);
        if (!findEdges.containsKey(n2)) findEdges.put(n2, new HashMap<N, E>());
        if (!findEdges.get(n2).containsKey(n1)) findEdges.get(n2).put(n1, edge);

        return edge;
    }

    // add a directed edge to the graph
    public E newEdge(N n1, N n2, Edge.Direction dir) {
        // if undirected, use undirected method
        if (dir == Edge.Direction.UNDIRECTED) return newEdge(n1, n2);

        E edge = createEdge(n1, n2, dir);
        edges.add(edge);

        // setup sets for n1
        if (!adj.containsKey(n1)) adj.put(n1, new HashSet<N>());
        if (dir == Edge.Direction.RIGHT && !succ.containsKey(n1)) succ.put(n1, new HashSet<N>());
        else if (dir == Edge.Direction.LEFT && !pred.containsKey(n1)) pred.put(n1, new HashSet<N>());
        // setup sets for n2
        if (!adj.containsKey(n2)) adj.put(n2, new HashSet<N>());
        if (dir == Edge.Direction.RIGHT && !pred.containsKey(n2)) pred.put(n2, new HashSet<N>());
        else if (dir == Edge.Direction.LEFT && !succ.containsKey(n2)) succ.put(n2, new HashSet<N>());
        // add n2 to n1's sets
        adj.get(n1).add(n2);
        if (dir == Edge.Direction.RIGHT) succ.get(n1).add(n2);
        else if (dir == Edge.Direction.LEFT) pred.get(n1).add(n2);
        // add n2 to n2's sets
        adj.get(n2).add(n1);
        if (dir == Edge.Direction.RIGHT) pred.get(n2).add(n1);
        else if (dir == Edge.Direction.LEFT) succ.get(n2).add(n1);

        return edge;
    }

    protected abstract E createEdge(N n1, N n2);
    protected abstract E createEdge(N n1, N n2, Edge.Direction dir);


}
