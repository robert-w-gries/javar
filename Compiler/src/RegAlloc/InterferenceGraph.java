package RegAlloc;

import Temp.Temp;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 11:03 PM
 */
public class InterferenceGraph extends Graph.Graph<InterferenceGraph.Node, InterferenceGraph.Edge> {

    private Set<Node> removed;

    public InterferenceGraph() {
        removed = new HashSet<>();
    }

    @Override
    protected InterferenceGraph.Edge createEdge(Node n1, Node n2, Edge.Direction dir) {
        return null;
    }

    @Override
    protected InterferenceGraph.Edge createEdge(Node n1, Node n2) {
        return new InterferenceGraph.Edge(n1, n2);
    }

    public void addMoveEdge(Node n1, Node n2) {
        newEdge(n1, n2).setMove(true);
    }

    public void removeNode(Node n) {
        removed.add(n);
    }

    public Set<Node> getNodes() {
        Set<Node> nodes = new HashSet<>();
        for (Node node : this.nodes.keySet()) {
            if (!removed.contains(node)) nodes.add(node);
        }
        return nodes;
    }

    public int getDegree(Node n) {
        int degree = 0;
        for (Node t : this.adj.get(n)) {
            if (!removed.contains(t)) degree++;
        }
        return degree;
    }

    public Set<Node> getAdj(Node n) {
        Set<Node> adj = new HashSet<>();
        for (Node t : this.adj.get(n)) {
            if (!removed.contains(t)) adj.add(t);
        }
        return adj;
    }

    public InterferenceGraph.Edge getEdge(Node n1, Node n2) {
        return findEdges.get(n1).get(n2);
    }

    public Set<InterferenceGraph.Edge> getEdges() {
        Set<InterferenceGraph.Edge> edges = new HashSet<>();
        for (InterferenceGraph.Edge edge : this.edges) {
            if (!removed.contains(edge.getN1()) && !removed.contains(edge.getN1())) edges.add(edge);
        }
        return edges;
    }

    public void removeEdge(InterferenceGraph.Edge edge) {
        edges.remove(edge);
        adj.get(edge.getN1()).remove(edge.getN2());
        adj.get(edge.getN2()).remove(edge.getN1());
        findEdges.get(edge.getN1()).remove(edge.getN2());
        findEdges.get(edge.getN2()).remove(edge.getN1());
    }

    public static class Edge extends Graph.Edge<InterferenceGraph.Node> {

        private boolean move, coalesced;

        public Edge(Node n1, Node n2) {
            super(n1, n2);
        }

        public boolean isMoveEdge() {
            return move;
        }

        public void setMove(boolean move) {
            this.move = move;
        }

        public boolean isCoalescedEdge() {
            return coalesced;
        }

        public void setCoalesced(boolean coalesced) {
            this.coalesced = coalesced;
            if (coalesced) move = false; // we don't want to consider this an "edge" of any kind anymore
        }
    }

    public static class Node extends Graph.Node<Temp> {

        private Temp color;

        public Node(Temp t) {
            super(t);
        }

        public Temp getColor() {
            return color;
        }

        public void setColor(Temp color) {
            this.color = color;
        }

    }

}
