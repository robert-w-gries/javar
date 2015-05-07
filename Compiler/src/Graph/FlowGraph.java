package Graph;

import Assem.Instr;
import Assem.LABEL;
import Temp.Label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:59 PM
 */
public class FlowGraph extends Graph<Instr, Node<Instr>> {

    public FlowGraph(List<Instr> code) {
        super(true);

        Map<Label, Node<Instr>> labelMap = new HashMap<>();

        // add label nodes so that jumps can be added right away
        for (Instr inst : code) {
            if (inst instanceof LABEL) labelMap.put(((LABEL)inst).label, newNode(inst));
        }

        Node<Instr> prev = null;
        for (Instr inst : code) {
            Node<Instr> n;
            if (inst instanceof LABEL) n = labelMap.get(((LABEL)inst).label);
            else n = newNode(inst);

            // if the previous instruction falls to this one, add it as a pred
            if (prev != null) prev.addSucc(n);

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

    @Override
    protected Node<Instr> createNode(Instr i, boolean bidirectional) { return new Node<>(i, bidirectional); }
}
