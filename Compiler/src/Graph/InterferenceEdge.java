package Graph;

public class InterferenceEdge extends Edge<InterferenceNode> {

    private boolean move, coalesced;

    public InterferenceEdge(InterferenceNode n1, InterferenceNode n2) {
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
