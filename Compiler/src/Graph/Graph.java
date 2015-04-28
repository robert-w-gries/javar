package Graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:41 PM
 */
public class Graph<T> {

    private int nodecount = 0;
    private Set<Node> nodes;

    public Graph() {
        nodes = new HashSet<>();
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Node newNode() {
        Node n = new Node(nodecount++);
        nodes.add(n);
        return n;
    }

    public class Node {
        private T element;
        private final int id;
        private Set<Node> succ, pred;

        public Node(int id) {
            this.id = id;
            this.succ = new HashSet<>();
            this.pred = new HashSet<>();
        }

        public int getId() {
            return id;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T el) {
            element = el;
        }

        public void addSucc(Node n) {
            succ.add(n);
            n.pred.add(this);
        }

        public void addPred(Node n) {
            pred.add(n);
            n.succ.add(n);
        }

        public void removeSucc(Node n) {
            succ.remove(n);
            n.pred.remove(this);
        }

        public void removePred(Node n) {
            pred.remove(n);
            n.succ.remove(this);
        }

        public Set<Node> getSucc() { return succ; }
        public Set<Node> getPred() { return pred; }

        public Set<Node> getAdjacent() {
            Set<Node> adj = new HashSet<>(pred);
            adj.addAll(succ);
            return adj;
        }
    }
}
