package RegAlloc;

import Assem.Instr;
import Assem.LABEL;
import Assem.MOVE;
import Temp.Label;
import Tree.JUMP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:59 PM
 */
public class FlowGraph extends Graph.Graph<Instr> {

    public FlowGraph(List<Instr> code) {
        Map<Label, Node> labelMap = new HashMap<>();

        // add label nodes so that jumps can be added right away
        for (Instr inst : code) {
            if (inst instanceof LABEL) labelMap.put(((LABEL)inst).label, newNode(inst));
        }

        Node prev = null;
        for (Instr inst : code) {
            Node n = newNode(inst);

            // if the previous instruction falls to this one, add it as a pred
            if (prev != null) n.addPred(prev);

            // add a succ for every jump
            for (Label l : inst.jumps) {
                n.addSucc(labelMap.get(l));
            }

            // if there were no jumps, it will always move to the next one
            if (inst.jumps.size() == 0) prev = n;
            // otherwise, we don't want the next node to have this one as a pred
            else prev = null;
        }
    }

    /**
     * The set of temporaries defined by this instruction or block
     */
    public List<Temp.Temp> def(Node node) {
        return node.getElement().def;
    }

    /**
     * The set of temporaries used by this instruction or block
     */
    public List<Temp.Temp> use(Node node) {
        return node.getElement().use;
    }

    /**
     * True if this node represents a <strong>move</strong> instruction,
     * i.e. one that can be deleted if def=use.
     */
    public boolean isMove(Node node) {
        return node.getElement() instanceof MOVE;
    }
}
