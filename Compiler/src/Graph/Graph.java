package Graph;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:41 PM
 */
public class Graph {

    int nodecount = 0;
    public List<Node> nodes;

    public Node newNode() {
        return new Node(this);
    }

    void check(Node n) {
        if (n.mygraph != this)
            throw new Error("Graph.addEdge using nodes from the wrong graph");
    }

    public void addEdge(Node from, Node to) {
        check(from);
        check(to);
        if (from.goesTo(to)) return;
        to.preds.add(0, from);
        from.succs.add(0, to);
    }

    public void rmEdge(Node from, Node to) {
        to.preds.remove(from);
        from.preds.remove(to);
    }
}
