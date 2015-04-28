package Graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:41 PM
 */
public class Graph {

    private int nodecount = 0;
    private List<Node> nodes;

    public Graph() {
        nodes = new LinkedList<>();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Node newNode() {
        Node n = new Node();
        n.setKey(nodecount++);
        n.setGraph(this);
        nodes.add(n);
        return n;
    }

    private void check(Node n) {
        if (n.getGraph() != this) throw new Error("Graph.addEdge using nodes from the wrong graph");
    }

    public void addEdge(Node from, Node to) {
        check(from);
        check(to);

        if (from.goesTo(to)) return;
        to.addPred(from);
        from.addPred(to);
    }

    public void rmEdge(Node from, Node to) {
        to.getPreds().remove(from);
        from.getPreds().remove(to);
    }
}
