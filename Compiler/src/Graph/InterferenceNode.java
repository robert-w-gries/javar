package Graph;

import Temp.Temp;

import java.util.HashSet;
import java.util.Set;

public class InterferenceNode extends Node<Temp> {

    private Temp color;
    private Set<Temp> coalescedTemps;
    private Set<InterferenceNode> moves;
    private boolean removed;

    public InterferenceNode(Temp t) {
        super(t, false);
        coalescedTemps = new HashSet<>();
        moves = new HashSet<>();
        removed = false;
    }

    public Temp getColor() {
        return color;
    }

    public void setColor(Temp color) {
        this.color = color;
    }

    public Set<InterferenceNode> getMoves() {
        return moves;
    }

    public void addMove(InterferenceNode node) {
        this.moves.add(node);
        node.moves.add(this);
    }

    public void removeMove(InterferenceNode node) {
        this.moves.remove(node);
        node.moves.remove(this);
    }

    public Set<Temp> getCoalescedTemps() {
        return coalescedTemps;
    }

    public void addCoalescedTemp(Temp t) {
        coalescedTemps.add(t);
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public Set<Node<Temp>> getAdj() {
        Set<Node<Temp>> adj = new HashSet<>();
        for (Node<Temp> node : this.adj) {
            if (!((InterferenceNode)node).removed) adj.add(node);
        }
        return adj;
    }

    @Override
    public int getDegree() {
        int degree = 0;
        for (Node<Temp> node : adj) {
            if (!((InterferenceNode)node).removed) degree++;
        }
        return degree;
    }

    void coalesceWith(InterferenceNode node) {
        this.coalescedTemps.add(node.value);
        this.coalescedTemps.addAll(node.coalescedTemps);

        for (Node<Temp> n : node.adj) {
            InterferenceNode in = (InterferenceNode)n;
            if (!this.adj.contains(in)) addEdge(in);
            in.removeEdge(node);
        }

        for (InterferenceNode m : node.moves) {
            if (!this.moves.contains(m)) addMove(m);
            m.removeMove(node);
        }
    }
}
