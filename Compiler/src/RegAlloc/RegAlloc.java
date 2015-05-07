package RegAlloc;

import Assem.Instr;
import Assem.MOVE;
import Graph.*;
import Mips.MipsFrame;
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
            boolean brk = true;
            for (Node<Instr> n : f.getNodes())
                if (!in.get(n).equals(in_.get(n)) || !out.get(n).equals(out_.get(n)))
                    brk = false;
            if (brk) break;
        }

        // TODO this prints the liveness info and kills the program
        printLivenessInfo(f, in, out);
        //System.exit(0);

        // create interference graph
        InterferenceGraph inter = new InterferenceGraph();
        // add all move edges
        for (Node<Instr> n : out.keySet()) {
            if (n.getValue() instanceof MOVE) {
                MOVE m = (MOVE)n.getValue();
                inter.addMoveRelatedTemp(m.dst());
                inter.addMoveRelatedTemp(m.src());
                inter.node(m.dst()).addMove(inter.node(m.src()));
            }
        }
        System.out.println(inter.toString());
        //System.exit(0);
        // for each instruction
        for (Node<Instr> n : out.keySet()) {
            // for each def of that instruction
            if (n.getValue().def != null) for (Temp def : n.getValue().def) {
                // for each temp that is live out from that instruction
                for (Temp out_temp : out.get(n)) {
                    // we add an edge between the def and the out_temp unless the out_temp is the source register, then we add a move edge
                    if (n.getValue() instanceof MOVE && out_temp.equals(((MOVE)n.getValue()).src())) continue;
                    else inter.node(def).addEdge(inter.node(out_temp));
                }
            }
            if (n.getValue() instanceof MOVE) {
                InterferenceNode src = inter.node(((MOVE)n.getValue()).src()), dst = inter.node(((MOVE)n.getValue()).dst());
                inter.addMoveRelatedTemp(src.getValue());
                inter.addMoveRelatedTemp(dst.getValue());
            }
        }
        // TODO this prints the interference graph and kills the program
        System.out.println(inter.toString());
        //System.exit(0);
        return inter;
    }

    private void printLivenessInfo(FlowGraph f, Map<Node<Instr>, Set<Temp>> in, Map<Node<Instr>, Set<Temp>> out) {
        System.out.format("%-40s%-50s%-50s%-100s%s\n\n", "Instruction", "|Defs", "|Uses", "|Ins", "|Outs");
        for (Instr inst : this.code) {
            Node<Instr> node = f.node(inst);
            // column 1: instruction
            String instruction = inst.toString();
            // column 2: def set
            String defs = "|";
            if (inst.def != null) {
                for (Temp t : inst.def) defs += t.regIndex + " ";
            }
            // column 3: use set
            String uses = "|";
            if (inst.use != null) {
                for (Temp t : inst.use) uses += t.regIndex + " ";
            }
            // column 4: in set
            String ins = "|";
            for (Temp t : in.get(node)) ins += t.regIndex + " ";
            // column 5: out set
            String outs = "|";
            for (Temp t : out.get(node)) outs += t.regIndex + " ";
            System.out.format("%-40s%-50s%-50s%-100s%s\n", instruction, defs, uses, ins, outs);
        }
    }

    private InterferenceGraph build() {
        FlowGraph flowGraph = new FlowGraph(code);
        return livenessAnalysis(flowGraph);
    }

    private InterferenceNode findSimplifiableNode(InterferenceGraph graph) {
        for (InterferenceNode node : graph.getNodes()) {
            if (!graph.isMoveRelatedNode(node) && !node.isPrecolored(this.frame) && node.getDegree() < this.frame.numRegs())
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
            if (graph.isMoveRelatedNode(node) && node.getDegree() < this.frame.numRegs()) return node;
        }
        return null;
    }

    private void freezeNode(InterferenceGraph graph, InterferenceNode node) {
        // remove all move edges connected to this node
        for (InterferenceNode n : new HashSet<>(node.getMoves())) {
            node.removeMove(n);
        }
        // this node is no longer to be considered move-related
        graph.removeMoveRelatedTemp(node.getValue());
        for (Temp t : node.getCoalescedTemps()) graph.removeMoveRelatedTemp(t);
    }

    private InterferenceNode findPotentialSpillNode(InterferenceGraph graph) {
        for (InterferenceNode node : graph.getNodes()) {
            if (!node.isPrecolored(this.frame)) return node;
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

        return okColors.iterator().next();

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
                    node.setRemoved(true);
                    continue;
                }

                // coalesce
                if (tryToCoalesceNodes(graph)) continue;

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
                    node.setRemoved(true);
                    continue;
                }

                // ready for selection
                break;
            }

            // TODO select - pop the entire stack, assigning colors
            List<InterferenceNode> spilledNodes = new ArrayList<>();
            while (!stack.isEmpty()) {

                InterferenceNode popNode = stack.pop();
                popNode.setRemoved(false);
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
        if (left == null) return new HashSet<>(right);
        if (right == null) return new HashSet<>(left);
        Set<T> set = new HashSet<>();
        set.addAll(left);
        set.addAll(right);
        return set;
    }

    private <T> Set<T> subtract(Collection<T> left, Collection<T> right) {
        if (left == null) return new HashSet<>();
        if (right == null) return new HashSet<>(left);
        Set<T> set = new HashSet<>();
        for (T t : left) if (!right.contains(t)) set.add(t);
        return set;
    }
}
