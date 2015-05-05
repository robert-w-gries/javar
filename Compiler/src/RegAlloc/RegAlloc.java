package RegAlloc;

import Assem.Instr;
import Assem.MOVE;
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
    private Stack<Temp> stack;

    public RegAlloc(Frame.Frame frame, List<Instr> code) {
        this.frame = frame;
        this.code = code;
        this.stack = new Stack<>();
        allocate();
    }

    private InterferenceGraph livenessAnalysis(FlowGraph f) {
        Map<Instr, Set<Temp>> in = new HashMap<>();
        Map<Instr, Set<Temp>> out = new HashMap<>();
        // add in and out sets for each node
        for (Instr n : f.getNodes()) {
            in.put(n, new HashSet<Temp>());
            out.put(n, new HashSet<Temp>());
        }
        // compute in and out sets for each node
        while (true) {
            // in' and out'
            Map<Instr, Set<Temp>> in_ = new HashMap<>();
            Map<Instr, Set<Temp>> out_ = new HashMap<>();
            for (Instr n : f.getNodes()) {
                // in'[n] <- in[n]; out'[n] <- out[n]
                in_.put(n, new HashSet<>(in.get(n)));
                out_.put(n, new HashSet<>(out.get(n)));
                // in[n] <- use[n] UNION (out[n] - def[n])
                in.put(n, union(n.use, subtract(out.get(n), n.def)));
                // out[n] <- UNION(s in succ[n]) in[s]
                out.put(n, new HashSet<Temp>());
                for (Instr s : f.getSucc(n)) out.put(n, union(out.get(n), in.get(s)));
            }
            // until in'[n] = in[n] and out'[n] = out[n] for all n
            boolean brk = false;
            for (Instr n : f.getNodes())
                if (in.get(n).equals(in_.get(n)) && out.get(n).equals(out_.get(n)))
                    brk = true;
            if (brk) break;
        }
        // create interference graph
        InterferenceGraph inter = new InterferenceGraph();
        // for each instruction
        for (Instr n : out.keySet()) {
            // for each def of that instruction
            for (Temp def : n.def) {
                // for each temp that is live out from that instruction
                for (Temp o : out.get(n)) {
                    if (o == def) continue; // ignore self
                    if (n instanceof MOVE) {
                        // if the instruction is a move, we ignore the source register
                        if (o != ((MOVE)n).src()) inter.newEdge(def, o);
                        else inter.addMoveEdge(def, o);
                    } else {
                        // otherwise, add an edge
                        inter.newEdge(def, o);
                    }
                }
            }
            if (n instanceof MOVE) {
                ((MOVE)n).src().setMoveRelated(true);
                ((MOVE)n).dst().setMoveRelated(true);
            }
        }
        return inter;
    }

    private InterferenceGraph build() {
        FlowGraph flowGraph = new FlowGraph(code);
        return livenessAnalysis(flowGraph);
    }

    private Temp findSimplifiableNode(InterferenceGraph graph) {
        for (Temp node : graph.getNodes()) {
            if (!node.isMoveRelated() && !this.frame.isRealRegister(node) && graph.getDegree(node) < this.frame.numRegs())
                return node;
        }
        return null; // can't simplify graph
    }

    private InterferenceGraph.Edge findCoalescableEdge(InterferenceGraph graph) {
        edge_loop: for (InterferenceGraph.Edge edge : graph.getEdges()) {
            if (!edge.isMoveEdge()) continue; // must be a move edge
            // every neighbor of n1 must have degree < K or be a neighbor of n2
            for (Temp n : graph.getAdj(edge.getN1())) {
                if (graph.getDegree(n) >= this.frame.numRegs() && !graph.getAdj(edge.getN2()).contains(n))
                    continue edge_loop; // if degree(n) >= K and n is not an element of adj(b), this is not a valid edge
            }
            // every neighbor of n2 must have degree < K or be a neighbor of n1
            for (Temp n : graph.getAdj(edge.getN2())) {
                if (graph.getDegree(n) >= this.frame.numRegs() && !graph.getAdj(edge.getN1()).contains(n))
                    continue edge_loop; // if degree(n) >= K and n is not an element of adj(a), this is not a valid edge
            }
            // if we are here, it means that all neighbors of both sides are valid and we can use this edge
            return edge;
        }
        return null; // can't coalesce any edges in graph
    }

    private Temp findFreezableNode(InterferenceGraph graph) {
        for (Temp node : graph.getNodes()) {
            if (node.isMoveRelated() && graph.getDegree(node) < this.frame.numRegs()) return node;
        }
        return null;
    }

    private void freezeNode(InterferenceGraph graph, Temp node) {
        // remove all move edges connected to this node
        for (Temp n : graph.getAdj(node)) {
            InterferenceGraph.Edge edge = graph.getEdge(node, n);
            if (edge.isMoveEdge()) graph.removeEdge(edge);
        }
        // this node is no longer to be considered move-related
        node.setMoveRelated(false);
    }

    private Temp findPotentialSpillNode(InterferenceGraph graph) {
        for (Temp node : graph.getNodes()) {
            if (node.regIndex >= this.frame.numRegs()) return node;
        }
        return null;
    }

    private void allocate() {
        boolean done = false;
        while (!done) {
            InterferenceGraph graph = build();

            while (true) {
                // simplify
                Temp node = findSimplifiableNode(graph);
                if (node != null) {
                    stack.push(node);
                    graph.removeNode(node);
                    continue;
                }

                // coalesce
                InterferenceGraph.Edge edge = findCoalescableEdge(graph);
                if (edge != null) {
                    edge.setCoalesced(true);
                    continue;
                }

                // freeze
                node = findFreezableNode(graph);
                if (node != null) {
                    freezeNode(graph, node);
                    continue;
                }

                // potential spill
                node = findPotentialSpillNode(graph);
                if (node != null) {
                    stack.push(node);
                    graph.removeNode(node);
                    continue;
                }

                // ready for selection
                break;
            }

            // TODO select - pop the entire stack, assigning colors
            // TODO actual spill - check for actual spills, modify the program and start over the whole process if so
            done = true;
        }
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
