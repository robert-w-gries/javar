package RegAlloc;

import Assem.Instr;
import Assem.MOVE;
import Graph.Graph;
import Temp.Temp;

import java.util.*;

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

        FlowGraph flowGraph = new FlowGraph(code);
        InterferenceGraph interferenceGraph = livenessAnalysis(flowGraph);
        allocate(interferenceGraph);
    }

    private InterferenceGraph livenessAnalysis(FlowGraph f) {
        Map<FlowGraph.Node, Set<Temp>> in = new HashMap<>();
        Map<FlowGraph.Node, Set<Temp>> out = new HashMap<>();

        // add in and out sets for each node
        for (FlowGraph.Node n : f.getNodes()) {
            in.put(n, new HashSet<Temp>());
            out.put(n, new HashSet<Temp>());
        }

        // compute in and out sets for each node
        while (true) {
            // in' and out'
            Map<FlowGraph.Node, Set<Temp>> in_ = new HashMap<>();
            Map<FlowGraph.Node, Set<Temp>> out_ = new HashMap<>();

            for (FlowGraph.Node n : f.getNodes()) {
                // in'[n] <- in[n]; out'[n] <- out[n]
                in_.put(n, new HashSet<>(in.get(n)));
                out_.put(n, new HashSet<>(out.get(n)));
                // in[n] <- use[n] UNION (out[n] - def[n])
                in.put(n, union(n.getElement().use, subtract(out.get(n), n.getElement().def)));
                // out[n] <- UNION(s in succ[n]) in[s]
                out.put(n, new HashSet<Temp>());
                for (FlowGraph.Node s : n.getSucc()) out.put(n, union(out.get(n), in.get(s)));
            }

            // until in'[n] = in[n] and out'[n] = out[n] for all n
            boolean brk = false;
            for (FlowGraph.Node n : f.getNodes())
                if (in.get(n).equals(in_.get(n)) && out.get(n).equals(out_.get(n)))
                    brk = true;
            if (brk) break;
        }

        // create interference graph
        InterferenceGraph inter = new InterferenceGraph();
        // for each instruction
        for (FlowGraph.Node n : out.keySet()) {
            // for each def of that instruction
            for (Temp def : n.getElement().def) {
                // for each temp that is live out from that instruction
                for (Temp o : out.get(n)) {
                    if (o == def) continue; // ignore self
                    if (n.getElement() instanceof MOVE) {
                        // if the instruction is a move, we ignore the source register
                        if (o != ((MOVE)n.getElement()).src()) inter.addEdge(def, o);
                    } else {
                        // otherwise, add an edge
                        inter.addEdge(def, o);
                    }
                }
            }
        }
        return inter;
    }

    private void allocate(InterferenceGraph inter) {
        // TODO simplify - One at a time, remove non-move-related nodes of low (< K) degree from the graph
        // TODO coallesce - Coallesce moves into single nodes and jump back to simplify
        // TODO freeze - freeze moves that don't apply to simplify or coallesce, jump back to simplify
        // TODO potential spill - select a high degree node and push it on the stack, jump back to simplify
        // TODO select - pop the entire stack, assigning colors
        // TODO actual spill - check for actual spills, modify the program and start over the whole process if so

        // TODO if we made it here, we're good
    }

    private <T> Set<T> union(Collection<T> left, Collection<T> right) {
        Set<T> set = new HashSet<>();
        set.addAll(left);
        set.addAll(right);
        return set;
    }

    private <T> Set<T> subtract(Collection<T> left, Collection<T> right) {
        Set<T> set = new HashSet<>();
        for (T t : left) if (!right.contains(t)) set.add(t);
        return set;
    }
}
