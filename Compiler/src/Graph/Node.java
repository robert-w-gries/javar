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
    private Graph graph;
    private int key;

    private List<Node> succs;
    private List<Node> preds;

    public Node() {
        succs = new LinkedList<>();
        preds = new LinkedList<>();
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph g) {
        graph = g;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void addSucc(Node n) {
        succs.add(n);
    }

    public void addPred(Node n) {
        preds.add(n);
    }

    public List<Node> getPreds() {
        return preds;
    }

    public List<Node> getSuccs() {
        return succs;
    }

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

    public boolean isAdjacent(Node n) {
        return goesTo(n) || comesFrom(n);
    }

    public String toString() {
        return String.valueOf(key);
    }
}
