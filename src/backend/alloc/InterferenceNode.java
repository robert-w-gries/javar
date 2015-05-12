package backend.alloc;

import arch.Frame;
import arch.Temp;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class InterferenceNode extends Node<Temp> {

    private Temp color;
    private final Set<Temp> coalescedTemps;
    private final Set<InterferenceNode> moves;
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
        if (this.equals(node) || this.adj.contains(node)) return;
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

    public void clearCoalescedTemps() {
        coalescedTemps.clear();
    }

    public boolean isPrecolored(Frame frame) {
        if (value.getRegIndex() < frame.numRegs()) return true;
        for (Temp t : coalescedTemps) if (t.getRegIndex() < frame.numRegs()) return true;
        return false;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public void addEdge(Node<Temp> node) {
        super.addEdge(node);
        removeMove((InterferenceNode)node);
    }

    @Override
    public Set<Node<Temp>> getAdj() {
        return this.adj.stream()
            .filter(node -> !((InterferenceNode) node).removed)
            .collect(Collectors.toSet());
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
        if (this.color == null) this.color = node.color;
        this.coalescedTemps.add(node.value);
        this.coalescedTemps.addAll(node.coalescedTemps);

        for (Node<Temp> n : new HashSet<>(node.adj)) {
            InterferenceNode in = (InterferenceNode)n;
            if (!this.adj.contains(in)) addEdge(in);
            in.removeEdge(node);
        }

        for (InterferenceNode m : new HashSet<>(node.moves)) {
            if (!this.moves.contains(m)) addMove(m);
            m.removeMove(node);
        }
    }

    public String toString() {
        String str = super.toString();
        for (Temp t : coalescedTemps) str += " " + t.toString();
        return str;
    }
}
