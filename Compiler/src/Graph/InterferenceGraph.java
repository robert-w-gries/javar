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

    public InterferenceGraph() {
        super(false);
    }

    @Override
    protected InterferenceNode createNode(Temp t, boolean bidirectional) {
        return new InterferenceNode(t);
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
        this.nodes.remove(n2);
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

