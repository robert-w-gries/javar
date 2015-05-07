package Graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:41 PM
 */
public abstract class Graph<T, N extends Node<T>> {

    protected Set<N> nodes;
    protected boolean bidirectional;

    protected Graph(boolean bidirectional) {
        nodes = new HashSet<>();
        this.bidirectional = bidirectional;
    }

    public Set<N> getNodes() {
        return nodes;
    }

    public N newNode(T el) {
        N n = createNode(el, bidirectional);
        nodes.add(n);
        return n;
    }

    protected abstract N createNode(T el, boolean bidirectional);

    public void removeNode(N node) {
        nodes.remove(node);
    }
}
