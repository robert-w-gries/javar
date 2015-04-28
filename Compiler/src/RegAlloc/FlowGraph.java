package RegAlloc;

import Assem.Instr;
import Assem.MOVE;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:59 PM
 */
public class FlowGraph extends Graph.Graph<Instr> {

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
