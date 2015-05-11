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

    private List<Instr> code;

    public FlowGraph(List<Instr> code) {
        super(true);
        this.code = code;

        // add a node for each instruction before adding edges
        for (Instr inst : code) node(inst);

        // prev is used to indicate an instruction that always falls to the current one
        Node<Instr> prev = null;
        for (Instr inst : code) {
            Node<Instr> n = node(inst);

            // if the previous instruction falls to this one, add an edge
            if (prev != null) prev.addSucc(n);

            // add a succ for every jump
            if (inst.jumps != null) {
                for (Label l : inst.jumps) {
                    if (lookup.containsKey(new LABEL("", l))) n.addSucc(lookup.get(new LABEL("", l)));
                }
            }

            // if there were no jumps, it will always fall to the next one
            if (inst.jumps == null || inst.jumps.size() == 0) prev = n;
            // otherwise, we don't want the next node to have this one as a pred
            else prev = null;
        }
    }

    @Override
    protected Node<Instr> createNode(Instr i, boolean bidirectional) { return new Node<>(i, bidirectional); }

    public String toString() {
        String print = "Control-flow Graph:\n\n";
        for (Instr inst : this.code) {
            print += inst.toString() + "\t\t\t\t\t\t\tnext:\t";
            Node<Instr> node = this.node(inst);
            for (Node<Instr> s : node.succ) {
                if (s.getValue() instanceof LABEL) print += ((LABEL)s.getValue()).label.toString() + " ";
                else print += s.getValue().toString() + " ";
            }
            print += "\n";
        }
        return print;
    }
}
