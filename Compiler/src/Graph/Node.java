package Graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:42 PM
 */
public class Node {
    public Graph mygraph;

    int mykey;

    public Node(Graph g) {
        mygraph = g;
        mykey = g.nodecount++;
        g.nodes.add(this);
    }

    List<Node> succs;
    List<Node> preds;

    public List<Node> adj() {
        List<Node> nodes = succs;
        nodes.addAll(preds);
        return nodes;
    }

    public int inDegree() {
        return preds.size();
    }

    public int outDegree() {
        return succs.size();
    }

    public int degree() {
        return inDegree() + outDegree();
    }

    public boolean goesTo(Node n) {
        return succs.contains(n);
    }

    public boolean comesFrom(Node n) {
        return preds.contains(n);
    }

    public boolean adj(Node n) {
        return goesTo(n) || comesFrom(n);
    }

    public String toString() {
        return String.valueOf(mykey);
    }
}
