package RegAlloc;

import Assem.Instr;
import Assem.MOVE;
import Graph.*;
import Mips.MipsFrame;
import Temp.Temp;
import Tree.TEMP;

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
    private Stack<InterferenceNode> stack;

    public RegAlloc(Frame.Frame frame, List<Instr> code) {
        this.frame = frame;
        this.code = code;
        this.stack = new Stack<>();
        allocate();
    }

    private InterferenceGraph livenessAnalysis(FlowGraph f) {
        Map<Node<Instr>, Set<Temp>> in = new HashMap<>();
        Map<Node<Instr>, Set<Temp>> out = new HashMap<>();
        // add in and out sets for each node
        for (Node<Instr> n : f.getNodes()) {
            in.put(n, new HashSet<Temp>());
            out.put(n, new HashSet<Temp>());
        }
        // compute in and out sets for each node
        while (true) {
            // in' and out'
            Map<Node<Instr>, Set<Temp>> in_ = new HashMap<>();
            Map<Node<Instr>, Set<Temp>> out_ = new HashMap<>();
            for (Node<Instr> n : f.getNodes()) {
                // in'[n] <- in[n]; out'[n] <- out[n]
                in_.put(n, new HashSet<>(in.get(n)));
                out_.put(n, new HashSet<>(out.get(n)));
                // in[n] <- use[n] UNION (out[n] - def[n])
                in.put(n, union(n.getValue().use, subtract(out.get(n), n.getValue().def)));
                // out[n] <- UNION(s in succ[n]) in[s]
                out.put(n, new HashSet<Temp>());
                for (Node<Instr> s : n.getSucc()) out.put(n, union(out.get(n), in.get(s)));
            }
            // until in'[n] = in[n] and out'[n] = out[n] for all n
            boolean brk = false;
            for (Node<Instr> n : f.getNodes())
                if (in.get(n).equals(in_.get(n)) && out.get(n).equals(out_.get(n)))
                    brk = true;
            if (brk) break;
        }
        // create interference graph
        InterferenceGraph inter = new InterferenceGraph();
        // for each instruction
        for (Node<Instr> n : out.keySet()) {
            // for each def of that instruction
            for (Temp def : n.getValue().def) {
                // for each temp that is live out from that instruction
                for (Temp o : out.get(n)) {
                    if (o == def) continue; // ignore self
                    if (n.getValue() instanceof MOVE) {
                        // if the instruction is a move, we ignore the source register
                        if (o != ((MOVE)n.getValue()).src()) inter.newNode(def).addEdge(inter.newNode(o));
                        else inter.newNode(def).addMove(inter.newNode(o));
                    } else {
                        // otherwise, add an edge
                        inter.newNode(def).addEdge(inter.newNode(o));
                    }
                }
            }
            if (n.getValue() instanceof MOVE) {
                InterferenceNode src = inter.getNode(((MOVE)n.getValue()).src()), dst = inter.getNode(((MOVE)n.getValue()).dst());
                src.getValue().setMoveRelated(true);
                dst.getValue().setMoveRelated(true);
            }
        }
        return inter;
    }

    private InterferenceGraph build() {
        FlowGraph flowGraph = new FlowGraph(code);
        return livenessAnalysis(flowGraph);
    }

    private InterferenceNode findSimplifiableNode(InterferenceGraph graph) {
        for (InterferenceNode node : graph.getNodes()) {
            if (!node.getValue().isMoveRelated() && !this.frame.isRealRegister(node.getValue()) && node.getDegree() < this.frame.numRegs())
                return node;
        }
        return null; // can't simplify graph
    }

    private boolean tryToCoalesceNodes(InterferenceGraph graph) {
        for (InterferenceNode a : graph.getNodes()) {
            if (a.getMoves().size() == 0) continue;

            move_loop: for (InterferenceNode b : a.getMoves()) {
                for (Node<Temp> _n : a.getAdj()) {
                    InterferenceNode n = (InterferenceNode)_n;
                    if (n.getDegree() >= this.frame.numRegs() && !b.getAdj().contains(n))
                        continue move_loop; // if n.degree >= K && !b.adj.contains(n), a and b can't be coalesced
                }

                for (Node<Temp> _n : b.getAdj()) {
                    InterferenceNode n = (InterferenceNode)_n;
                    if (n.getDegree() >= this.frame.numRegs() && !a.getAdj().contains(n))
                        continue move_loop; // if n.degree >= K && !a.adj.contains(n), a and b can't be coalesced
                }

                graph.coalesceNodes(a, b);
                return true; // we were successfully able to coalesce two nodes
            }
        }
        return false; // no moves were coalescable
    }

    private InterferenceNode findFreezableNode(InterferenceGraph graph) {
        for (InterferenceNode node : graph.getNodes()) {
            if (node.getValue().isMoveRelated() && node.getDegree() < this.frame.numRegs()) return node;
        }
        return null;
    }

    private void freezeNode(InterferenceNode node) {
        // remove all move edges connected to this node
        for (InterferenceNode n : new HashSet<>(node.getMoves())) {
            node.removeMove(n);
        }
        // this node is no longer to be considered move-related
        node.getValue().setMoveRelated(false);
    }

    private InterferenceNode findPotentialSpillNode(InterferenceGraph graph) {
        for (InterferenceNode node : graph.getNodes()) {
            if (node.getValue().regIndex >= this.frame.numRegs()) return node;
        }
        return null;
    }

    private Temp selectColor(InterferenceNode node) {

        Set<Temp> okColors = new HashSet<>(MipsFrame.getAvailableRegs());

        for (Node<Temp> _adjNode : node.getAdj()) {
            InterferenceNode adjNode = (InterferenceNode)_adjNode;
            if (adjNode.getColor() != null) {
                okColors.remove(adjNode.getColor());
            }
        }

        if (okColors.isEmpty()) {
            return null;
        }

        return null;

    }

    private void allocate() {
        boolean done = false;
        while (true) {
            InterferenceGraph graph = build();

            while (true) {
                // simplify
                InterferenceNode node = findSimplifiableNode(graph);
                if (node != null) {
                    stack.push(node);
                    graph.removeNode(node);
                    continue;
                }

                // coalesce
                if (!tryToCoalesceNodes(graph)) continue;

                // freeze
                node = findFreezableNode(graph);
                if (node != null) {
                    freezeNode(node);
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
            List<InterferenceNode> spilledNodes = new ArrayList<>();
            while (!stack.isEmpty()) {

                InterferenceNode popNode = stack.pop();
                Temp selected = selectColor(popNode);

                if (selected == null) {
                    spilledNodes.add(popNode);
                    continue;
                }

                popNode.setColor(selected);

            }

            // TODO actual spill - check for actual spills, modify the program and start over the whole process if so
            if (!spilledNodes.isEmpty()) {
                //TODO: spill has occurred, let's rewrite the program!
                continue;
            }

            break;

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
