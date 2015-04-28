package RegAlloc;

import Assem.Instr;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/28/15
 * Time: 3:47 PM
 */
public class RegAlloc {

    private Frame.Frame frame;
    private List<Instr> code;

    public RegAlloc(Frame.Frame frame, List<Instr> code) {
        this.frame = frame;
        this.code = code;

        FlowGraph flowGraph = generateFlowGraph(code);
        // TODO produce interference graph from control flow graph

    }

    private FlowGraph generateFlowGraph(List<Instr> code) {
        FlowGraph g = new FlowGraph();

        // TODO produce control flow graph from code

        return g;
    }
}
