package Graph;

import Temp.Temp;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 11:03 PM
 */
public class InterferenceGraph extends Graph<Temp, InterferenceNode> {

    private Set<Temp> moveRelated;

    public InterferenceGraph() {
        super(false);
        moveRelated = new HashSet<>();
        for (int i = 0; i < 32; i++) {
            InterferenceNode n = node(new Temp(i));
            n.setColor(n.getValue());
        }
        for (int i = 0; i < 32; i++) for (int j = 0; j < 32; j++) node(new Temp(i)).addEdge(node(new Temp(j)));
    }

    @Override
    protected InterferenceNode createNode(Temp t, boolean bidirectional) {
        return new InterferenceNode(t);
    }

    public void addMoveRelatedTemp(Temp t) {
        moveRelated.add(t);
    }

    public void removeMoveRelatedTemp(Temp t) {
        moveRelated.remove(t);
    }

    public boolean isMoveRelatedNode(InterferenceNode n) {
        if (moveRelated.contains(n.getValue())) return true;
        for (Temp t : n.getCoalescedTemps()) if (moveRelated.contains(t)) return true;
        return false;
    }

    public Set<InterferenceNode> getNodes() {
        Set<InterferenceNode> nodes = new HashSet<>();
        for (InterferenceNode node : this.nodes) {
            if (!node.isRemoved()) nodes.add(node);
        }
        return nodes;
    }

    public void coalesceNodes(InterferenceNode n1, InterferenceNode n2) {
        n1.coalesceWith(n2);
        removeNode(n2);
        this.moveRelated.remove(n2.getValue());
    }

    public String toString() {
        String print = "Interference Graph:\n\n\t";
        for (InterferenceNode node : nodes) {
            print += "|" + node.getValue().regIndex;
            if (node.getValue().regIndex < 100) print += "\t";
        }
        print += "\n";
        for (InterferenceNode node : nodes) {
            print += node.getValue().regIndex + "\t";
            for (InterferenceNode node1 : nodes) {
                print += "|";
                if (node.getAdj().contains(node1)) print += "X";
                if (node.getMoves().contains(node1)) print += "M";
                print += "\t";
            }
            print += "\n";
        }
        return print;
    }
}

