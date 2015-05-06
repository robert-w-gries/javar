package Graph;

import Temp.Temp;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 11:03 PM
 */
public class InterferenceGraph extends Graph<Temp, InterferenceNode, InterferenceEdge> {

    private Set<InterferenceNode> removed;
    private Map<Temp, InterferenceNode> tempMap;

    public InterferenceGraph() {
        removed = new HashSet<>();
        tempMap = new HashMap<>();
    }

    public InterferenceNode getNode(Temp t) {
        return tempMap.get(t);
    }

    @Override
    protected InterferenceNode createNode(Temp t) {
        InterferenceNode n = new InterferenceNode(t);
        tempMap.put(t, n);
        return n;
    }

    @Override
    protected InterferenceEdge createEdge(InterferenceNode n1, InterferenceNode n2, Edge.Direction dir) {
        return null;
    }

    @Override
    protected InterferenceEdge createEdge(InterferenceNode n1, InterferenceNode n2) {
        return new InterferenceEdge(n1, n2);
    }

    public void addMoveEdge(InterferenceNode n1, InterferenceNode n2) {
        if (hasEdge(n1, n2)) return;
        newEdge(n1, n2).setMove(true);
    }

    public void removeNode(InterferenceNode n) {
        removed.add(n);
    }

    public Set<InterferenceNode> getNodes() {
        Set<InterferenceNode> nodes = new HashSet<>();
        for (InterferenceNode node : this.nodes.keySet()) {
            if (!removed.contains(node)) nodes.add(node);
        }
        return nodes;
    }

    public int getDegree(InterferenceNode n) {
        int degree = 0;
        for (InterferenceNode t : this.adj.get(n)) {
            if (!removed.contains(t)) degree++;
        }
        return degree;
    }

    public Set<InterferenceNode> getAdj(InterferenceNode n) {
        Set<InterferenceNode> adj = new HashSet<>();
        for (InterferenceNode t : this.adj.get(n)) {
            if (!removed.contains(t)) adj.add(t);
        }
        return adj;
    }

    @Override
    public InterferenceEdge newEdge(InterferenceNode n1, InterferenceNode n2) {
        if (hasEdge(n1, n2) && getEdge(n1, n2).isMoveEdge()) removeEdge(getEdge(n1, n2));
        return super.newEdge(n1, n2);
    }

    public InterferenceEdge getEdge(InterferenceNode n1, InterferenceNode n2) {
        return findEdges.get(n1).get(n2);
    }

    public Set<InterferenceEdge> getEdges() {
        Set<InterferenceEdge> edges = new HashSet<>();
        for (InterferenceEdge edge : this.edges) {
            if (!removed.contains(edge.getN1()) && !removed.contains(edge.getN1())) edges.add(edge);
        }
        return edges;
    }

    public void removeEdge(InterferenceEdge edge) {
        edges.remove(edge);
        adj.get(edge.getN1()).remove(edge.getN2());
        adj.get(edge.getN2()).remove(edge.getN1());
        findEdges.get(edge.getN1()).remove(edge.getN2());
        findEdges.get(edge.getN2()).remove(edge.getN1());
    }

    public boolean hasEdge(InterferenceNode n1, InterferenceNode n2) {
        return findEdges.containsKey(n1) && findEdges.get(n1).containsKey(n2);
    }

    public void coalesceEdge(InterferenceEdge edge) {
        InterferenceNode combined = edge.getN1(), toCoalesce = edge.getN2();
        // add toCoalesce's temps to combined
        combined.addCoalescedTemp(toCoalesce.getValue());
        for (Temp t : toCoalesce.getCoalescedTemps()) combined.addCoalescedTemp(t);

        // change toCoalesce's edges to use combined instead
        for (InterferenceEdge e : findEdges.get(toCoalesce).values()) {
            if (e != edge) {
                if (toCoalesce == e.getN1()) {
                    if (this.findEdges.get(combined).containsKey(e.getN2())) continue;
                    e.setN1(combined);
                } else if (toCoalesce == e.getN2()) {
                    if (this.findEdges.get(combined).containsKey(e.getN1())) continue;
                    e.setN2(combined);
                }
            }
        }

        // remove toCoalesce and edge from all data structures
        this.nodes.remove(toCoalesce);
        this.edges.remove(edge);
        for (InterferenceNode n : this.adj.get(toCoalesce)) {
            this.adj.get(n).remove(toCoalesce);
            this.findEdges.get(n).remove(toCoalesce);
        }
        this.adj.remove(toCoalesce);
        this.findEdges.remove(toCoalesce);
    }
}

