package Graph;

import Assem.Instr;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 5/5/15
 * Time: 4:52 PM
 */
public class FlowEdge extends Edge<FlowNode> {
    FlowEdge(FlowNode n1, FlowNode n2, Edge.Direction dir) {
        super(n1, n2, dir);
    }
}
