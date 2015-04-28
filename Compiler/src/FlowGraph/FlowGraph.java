package FlowGraph;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 10:59 PM
 */
public abstract class FlowGraph extends Graph.Graph {

    /**
     * The set of temporaries defined by this instruction or block
     */
    public abstract List<Temp.Temp> def(Node node);

    /**
     * The set of temporaries used by this instruction or block
     */
    public abstract List<Temp.Temp> use(Node node);

    /**
     * True if this node represents a <strong>move</strong> instruction,
     * i.e. one that can be deleted if def=use.
     */
    public abstract boolean isMove(Node node);
}
