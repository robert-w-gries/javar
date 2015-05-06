package Graph;

public class InterferenceEdge extends Edge<InterferenceNode> {

    private boolean move;

    public InterferenceEdge(InterferenceNode n1, InterferenceNode n2) {
        super(n1, n2);
    }

    public boolean isMoveEdge() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }
}
