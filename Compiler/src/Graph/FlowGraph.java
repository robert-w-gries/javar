package Graph;

import Assem.Instr;
import Assem.LABEL;
import Temp.Label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:59 PM
 */
public class FlowGraph extends Graph<Instr, FlowNode, FlowEdge> {

    public FlowGraph(List<Instr> code) {
        Map<Label, FlowNode> labelMap = new HashMap<>();

        // add label nodes so that jumps can be added right away
        for (Instr inst : code) {
            if (inst instanceof LABEL) labelMap.put(((LABEL)inst).label, newNode(inst));
        }

        FlowNode prev = null;
        for (Instr inst : code) {
            FlowNode n;
            if (inst instanceof LABEL) n = labelMap.get(((LABEL)inst).label);
            else n = newNode(inst);

            // if the previous instruction falls to this one, add it as a pred
            if (prev != null) newEdge(prev, n, Edge.Direction.RIGHT);

            // add a succ for every jump
            for (Label l : inst.jumps) {
                newEdge(n, labelMap.get(l), Edge.Direction.RIGHT);
            }

            // if there were no jumps, it will always move to the next one
            if (inst.jumps.size() == 0) prev = n;
            // otherwise, we don't want the next node to have this one as a pred
            else prev = null;
        }
    }

    public Set<FlowNode> getSucc(FlowNode node) {
        return succ.get(node);
    }

    @Override
    protected FlowNode createNode(Instr i) { return new FlowNode(i); }

    @Override
    protected FlowEdge createEdge(FlowNode n1, FlowNode n2) {
        return null;
    }

    @Override
    protected FlowEdge createEdge(FlowNode n1, FlowNode n2, Edge.Direction dir) {
        return new FlowEdge(n1, n2, dir);
    }
}
