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
public abstract class Graph<T extends Node, E extends Edge> {

    protected int nodecount = 0;
    protected Map<T, Integer> nodes;
    protected Set<E> edges;
    protected Map<T, Set<T>> succ, pred, adj;
    protected Map<T, Map<T, E>> findEdges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
        succ = new HashMap<>();
        pred = new HashMap<>();
        adj = new HashMap<>();
        findEdges = new HashMap<>();
    }

    public Set<T> getNodes() {
        return nodes.keySet();
    }

    public T newNode(T el) {
        nodes.put(el, nodecount++);
        return el;
    }

    // add an undirected edge to the graph
    public E newEdge(T n1, T n2) {
        E edge = createEdge(n1, n2);
        edges.add(edge);

        // setup sets for n1
        if (!succ.containsKey(n1)) succ.put(n1, new HashSet<T>());
        if (!pred.containsKey(n1)) pred.put(n1, new HashSet<T>());
        if (!adj.containsKey(n1)) adj.put(n1, new HashSet<T>());
        // setup sets for n2
        if (!succ.containsKey(n2)) succ.put(n2, new HashSet<T>());
        if (!pred.containsKey(n2)) pred.put(n2, new HashSet<T>());
        if (!adj.containsKey(n2)) adj.put(n2, new HashSet<T>());
        // add n2 to n1's sets
        succ.get(n1).add(n2);
        pred.get(n1).add(n2);
        adj.get(n1).add(n2);
        // add n1 to n2's sets
        succ.get(n2).add(n1);
        pred.get(n2).add(n1);
        adj.get(n2).add(n1);

        if (!findEdges.containsKey(n1)) findEdges.put(n1, new HashMap<T, E>());
        if (!findEdges.get(n1).containsKey(n2)) findEdges.get(n1).put(n2, edge);
        if (!findEdges.containsKey(n2)) findEdges.put(n2, new HashMap<T, E>());
        if (!findEdges.get(n2).containsKey(n1)) findEdges.get(n2).put(n1, edge);

        return edge;
    }

    // add a directed edge to the graph
    public E newEdge(T n1, T n2, Edge.Direction dir) {
        // if undirected, use undirected method
        if (dir == Edge.Direction.UNDIRECTED) return newEdge(n1, n2);

        E edge = createEdge(n1, n2, dir);
        edges.add(edge);

        // setup sets for n1
        if (!adj.containsKey(n1)) adj.put(n1, new HashSet<T>());
        if (dir == Edge.Direction.RIGHT && !succ.containsKey(n1)) succ.put(n1, new HashSet<T>());
        else if (dir == Edge.Direction.LEFT && !pred.containsKey(n1)) pred.put(n1, new HashSet<T>());
        // setup sets for n2
        if (!adj.containsKey(n2)) adj.put(n2, new HashSet<T>());
        if (dir == Edge.Direction.RIGHT && !pred.containsKey(n2)) pred.put(n2, new HashSet<T>());
        else if (dir == Edge.Direction.LEFT && !succ.containsKey(n2)) succ.put(n2, new HashSet<T>());
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

    protected abstract E createEdge(T n1, T n2);
    protected abstract E createEdge(T n1, T n2, Edge.Direction dir);


}
