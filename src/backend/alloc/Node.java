package backend.alloc;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by rgries on 5/5/15.
 */
public class Node<T> {

    protected T value;
    protected Set<Node<T>> succ, pred, adj;
    protected boolean bidirectional;

    Node(T value, boolean bidirectional) {
        this.value = value;
        this.bidirectional = bidirectional;
        if (bidirectional) {
            succ = new HashSet<>();
            pred = new HashSet<>();
        } else {
            adj = new HashSet<>();
        }
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Node && value.equals(((Node)o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Set<Node<T>> getSucc() {
        return succ;
    }

    public Set<Node<T>> getAdj() {
        return adj;
    }

    public void addSucc(Node<T> node) {
        this.succ.add(node);
        node.pred.add(this);
    }

    public void addPred(Node<T> node) {
        this.pred.add(node);
        node.succ.add(this);
    }

    public void removeSucc(Node<T> node) {
        this.succ.remove(node);
        node.pred.remove(this);
    }

    public void removePred(Node<T> node) {
        this.pred.remove(node);
        node.succ.remove(this);
    }

    public void addEdge(Node<T> node) {
        if (this.equals(node)) return;
        this.adj.add(node);
        node.adj.add(this);
    }

    public void removeEdge(Node<T> node) {
        this.adj.remove(node);
        node.adj.remove(this);
    }

    public int getDegree() {
        if (!bidirectional) return adj.size();
        else return succ.size() + pred.size();
    }

    public boolean hasEdge(Node<T> node) {
        return adj.contains(node);
    }

    public String toString() {
        return value.toString();
    }

}
