package backend.alloc;

import arch.Frame;
import backend.assem.Instr;
import backend.assem.MOVE;
import backend.assem.OPER;
import arch.mips.InFrame;
import arch.mips.MipsFrame;
import arch.Temp;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/28/15
 * Time: 3:47 PM
 */
public class RegAlloc {

    private final Frame frame;
    private final List<Instr> code;
    private final Stack<InterferenceNode> stack;
    private final Map<Temp, Temp> colorMap;

    public RegAlloc(Frame frame, List<Instr> code) {
        this.frame = frame;
        this.code = code;
        this.stack = new Stack<>();
        colorMap = new HashMap<>();
        allocate();
    }

    private InterferenceGraph livenessAnalysis(FlowGraph f) {
        Map<Node<Instr>, Set<Temp>> in = new HashMap<>();
        Map<Node<Instr>, Set<Temp>> out = new HashMap<>();
        // add in and out sets for each node
        for (Node<Instr> n : f.getNodes()) {
            in.put(n, new HashSet<>());
            out.put(n, new HashSet<>());
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
                in.put(n, union(n.getValue().getUse(), subtract(out.get(n), n.getValue().getDef())));
                // out[n] <- UNION(s in succ[n]) in[s]
                out.put(n, new HashSet<>());
                for (Node<Instr> s : n.getSucc()) out.put(n, union(out.get(n), in.get(s)));
            }
            // until in'[n] = in[n] and out'[n] = out[n] for all n
            boolean brk = true;
            for (Node<Instr> n : f.getNodes())
                if (!in.get(n).equals(in_.get(n)) || !out.get(n).equals(out_.get(n)))
                    brk = false;
            if (brk) break;
        }

        // create interference graph
        InterferenceGraph inter = new InterferenceGraph();
        // add all move edges
        out.keySet().stream()
            .filter(n -> n.getValue() instanceof MOVE)
            .forEach(n -> {
                MOVE m = (MOVE) n.getValue();
                inter.addMoveRelatedTemp(m.dst());
                inter.addMoveRelatedTemp(m.src());
                inter.node(m.dst()).addMove(inter.node(m.src()));
        });

        // for each instruction
        // for each def of that instruction
        // for each temp that is live out from that instruction
        // we add an edge between the def and the out_temp unless the out_temp is the source register, then we add a move edge
        out.keySet().stream()
            .filter(n -> n.getValue().getDef() != null)
            .forEach(n -> {
            for (Temp def : n.getValue().getDef()) {
                // for each temp that is live out from that instruction
                // we add an edge between the def and the out_temp unless the out_temp is the source register, then we add a move edge
                out.get(n).stream()
                    .filter(out_temp -> !(n.getValue() instanceof MOVE) || !out_temp.equals(((MOVE) n.getValue()).src()))
                    .forEach(out_temp -> inter.node(def).addEdge(inter.node(out_temp)));
            }
        });

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
            if (inst.getDef() != null) {
                for (Temp t : inst.getDef()) defs += t.getRegIndex() + " ";
            }
            // column 3: use set
            String uses = "|";
            if (inst.getDef() != null) {
                for (Temp t : inst.getDef()) uses += t.getRegIndex() + " ";
            }
            // column 4: in set
            String ins = "|";
            for (Temp t : in.get(node)) ins += t.getRegIndex() + " ";
            // column 5: out set
            String outs = "|";
            for (Temp t : out.get(node)) outs += t.getRegIndex() + " ";
            System.out.format("%-40s%-50s%-50s%-100s%s\n", instruction, defs, uses, ins, outs);
        }
    }

    private InterferenceGraph build() {
        FlowGraph flowGraph = new FlowGraph(code);
        return livenessAnalysis(flowGraph);
    }

    private InterferenceNode findSimplifiableNode(InterferenceGraph graph) {
        for (InterferenceNode node : graph.getNodes()) {
            if (!graph.isMoveRelatedNode(node) && !node.isPrecolored(this.frame) && node.getDegree() < this.frame.numColors())
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
                    if (n.getDegree() >= this.frame.numColors() && !b.getAdj().contains(n))
                        continue move_loop; // if n.degree >= K && !b.adj.contains(n), a and b can't be coalesced
                }

                for (Node<Temp> _n : b.getAdj()) {
                    InterferenceNode n = (InterferenceNode)_n;
                    if (n.getDegree() >= this.frame.numColors() && !a.getAdj().contains(n))
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
            if (graph.isMoveRelatedNode(node) && node.getDegree() < this.frame.numColors()) return node;
        }
        return null;
    }

    private void freezeNode(InterferenceGraph graph, InterferenceNode node) {
        // remove all move edges connected to this node
        new HashSet<>(node.getMoves()).forEach(node::removeMove);
        // this node is no longer to be considered move-related
        graph.removeMoveRelatedTemp(node.getValue());
        node.getCoalescedTemps().forEach(graph::removeMoveRelatedTemp);
    }

    private InterferenceNode findPotentialSpillNode(InterferenceGraph graph) {
        InterferenceNode spill = null;
        double priority = Double.MAX_VALUE;

        for (InterferenceNode node : graph.getNodes()) {
            if (!node.isPrecolored(this.frame)) {
                // loop through code, looking for uses and defs of this node
                int defs_uses = 0;
                for (Instr i : this.code) {
                    if (i.getDef() != null && i.getDef().contains(node.getValue())) defs_uses++;
                    if (i.getUse() != null && i.getUse().contains(node.getValue())) defs_uses++;
                }
                // calculate priority, look for smallest value
                double pri = (double)defs_uses / node.getDegree();
                if (pri < priority) {
                    priority = pri;
                    spill = node;
                }
            }
        }

        return spill;
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

        InterferenceGraph graph;

        while (true) {
            graph = build();

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

            // select
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

            // actual spill
            if (!spilledNodes.isEmpty()) {

                //allocate memory for each spilledNode
                for (InterferenceNode spilledNode : spilledNodes) {

                    spill(spilledNode.getValue());
                    spilledNode.getCoalescedTemps().forEach(this::spill);

                }

            } else {
                break;
            }

        }

        populateColorMap(graph);

        // set each temp to its color
        for (Instr inst : this.code) {
            if (inst.getDef() != null) for (Temp t : inst.getDef()) t.setRegIndex(colorMap.get(t).getRegIndex());
            if (inst.getUse() != null) for (Temp t : inst.getUse()) t.setRegIndex(colorMap.get(t).getRegIndex());
        }
    }

    private void spill(Temp spill) {
        int tempnum = spill.getRegIndex();
        InFrame f = (InFrame)frame.allocLocal();
        //create temp for each def/use
        for (int i = 0; i < code.size(); i++) {

            Instr line = code.get(i);
            //insert store instruction
            if (line.getDef() != null) for (int j = 0; j < line.getDef().size(); j++) {
                if (line.getDef().get(j).getRegIndex() == tempnum) {
                    if (line instanceof MOVE) {
                        code.set(i, OPER.sw(((MOVE)line).src(), new Temp(29), f.getOffset(), frame.getName().toString()));
                    } else {
                        line.getDef().set(j, new Temp());
                        code.add(i + 1, OPER.sw(line.getDef().get(j), new Temp(29), f.getOffset(), frame.getName().toString()));
                        i++;
                    }
                    break;
                }
            }

            //insert fetch instruction
            if (line.getUse() != null) for (int j = 0; j < line.getUse().size(); j++) {
                if (line.getUse().get(j).getRegIndex() == tempnum) {
                    if (line instanceof MOVE) {
                        code.set(i, OPER.lw(((MOVE)line).dst(), new Temp(29), f.getOffset(), frame.getName().toString()));
                    } else {
                        line.getUse().set(j, new Temp());
                        code.add(i, OPER.lw(line.getUse().get(j), new Temp(29), f.getOffset(), frame.getName().toString()));
                        i++;
                    }
                    break;
                }
            }
        }
    }

    private void populateColorMap(InterferenceGraph graph) {
        for (InterferenceNode node : graph.getNodes()) {
            colorMap.put(node.getValue(), node.getColor());
            for (Temp t : node.getCoalescedTemps()) {
                colorMap.put(t, node.getColor());
            }
        }
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
        return left.stream().filter(t -> !right.contains(t)).collect(Collectors.toSet());
    }
}
