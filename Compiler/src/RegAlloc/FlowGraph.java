package RegAlloc;

import Assem.Instr;
import Assem.LABEL;
import Assem.MOVE;
import Graph.Graph;
import Temp.Label;
import Temp.Temp;

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
public class FlowGraph extends Graph<Instr, Graph.Edge<Instr>> {

    public FlowGraph(List<Instr> code) {
        Map<Label, Instr> labelMap = new HashMap<>();

        // add label nodes so that jumps can be added right away
        for (Instr inst : code) {
            if (inst instanceof LABEL) labelMap.put(((LABEL)inst).label, newNode(inst));
        }

        Instr prev = null;
        for (Instr inst : code) {
            Instr n;
            if (inst instanceof LABEL) n = labelMap.get(((LABEL)inst).label);
            else n = newNode(inst);

            // if the previous instruction falls to this one, add it as a pred
            if (prev != null) newEdge(prev, n, Direction.RIGHT);

            // add a succ for every jump
            for (Label l : inst.jumps) {
                newEdge(n, labelMap.get(l), Direction.RIGHT);
            }

            // if there were no jumps, it will always move to the next one
            if (inst.jumps.size() == 0) prev = n;
            // otherwise, we don't want the next node to have this one as a pred
            else prev = null;
        }
    }

    public Set<Instr> getSucc(Instr node) {
        return succ.get(node);
    }

    /**
     * The set of temporaries defined by this instruction or block
     */
    public List<Temp> def(Instr node) {
        return node.def;
    }

    /**
     * The set of temporaries used by this instruction or block
     */
    public List<Temp> use(Instr node) {
        return node.use;
    }

    /**
     * True if this node represents a <strong>move</strong> instruction,
     * i.e. one that can be deleted if def=use.
     */
    public boolean isMove(Instr node) {
        return node instanceof MOVE;
    }

    @Override
    protected Graph.Edge<Instr> createEdge(Instr n1, Instr n2) {
        return new Graph.Edge<>(n1, n2);
    }

    @Override
    protected Graph.Edge<Instr> createEdge(Instr n1, Instr n2, Graph.Direction dir) {
        return new Graph.Edge<>(n1, n2, dir);
    }
}
