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

    private Map<Temp, InterferenceNode> tempMap;

    public InterferenceGraph() {
        super(false);
        tempMap = new HashMap<>();
    }

    public InterferenceNode getNode(Temp t) {
        return tempMap.get(t);
    }

    @Override
    protected InterferenceNode createNode(Temp t, boolean bidirectional) {
        InterferenceNode n = new InterferenceNode(t);
        tempMap.put(t, n);
        return n;
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
}

