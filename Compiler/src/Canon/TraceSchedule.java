package Canon;

import Temp.Label;
import Tree.CJUMP;
import Tree.JUMP;
import Tree.LABEL;
import Tree.Stm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TraceSchedule {

    public List<Stm> trace;
    private BasicBlocks blocks;
    private Map<Label, List<Stm>> unvisited = new HashMap<Label, List<Stm>>();

    /**
     * Initializes the unvisited map with all blocks and creates the trace
     * @param blocks the blocks to be converted to a single trace
     */
    public TraceSchedule(BasicBlocks blocks) {
        this.blocks = blocks;
        for (List<Stm> block : blocks.blocks) {
            unvisited.put(((LABEL)block.get(0)).label, block);
        }
        trace = new LinkedList<Stm>();
        create_trace();
    }

    /**
     * Loops through every block until an unvisited block is reached
     * The trace is built by iteratively following jumps until the only jump is to an already visited block,
     * after which the outer loop will continue until all blocks are visited
     */
    private void create_trace() {
        for (List<Stm> block : blocks.blocks) {
            // we will continue adding blocks to the trace until we hit a dead end
            while (true) {
                Label block_label = ((LABEL)block.get(0)).label;
                // we only want to add a block if it hasn't been visited already
                if (!unvisited.containsKey(block_label)) break;
                unvisited.remove(block_label); // now that we are adding it, mark it visited

                trace.addAll(block); // add the whole block to the trace

                // get the last statement of the block that was just added
                Stm last = trace.get(trace.size() - 1);

                // this statement will either be a JUMP or a CJUMP
                // use the JUMP or CJUMP to determine which block to continue with
                if (last instanceof JUMP) block = trace_jump((JUMP)last);
                else if (last instanceof CJUMP) block = trace_cjump((CJUMP)last);

                // if there was no block, we've hit a dead end and will look for the next unvisited block
                if (block == null) break;
            }
        }

        trace.add(new LABEL(blocks.done)); // add the done label to the end of the whole trace
    }

    /**
     * Attempts to follow a JUMP to its target if the target is not yet visited
     * @param jump a JUMP to follow
     * @return the next block to add to the trace, if one was found
     */
    private List<Stm> trace_jump(JUMP jump) {
        // if the unvisited contains the jump target, it hasn't yet been added to the trace
        if (unvisited.containsKey(jump.target)) {
            // in this case, we can add the block immediately after its jump and the jump is unnecessary
            trace.remove(trace.size() - 1);
            return unvisited.get(jump.target);
        } else {
            // if it was not found, we've hit a dead end
            return null;
        }
    }

    /**
     * Attempts to follow a CJUMP to either of its targets if either of them is not yet visited
     * @param cj a CJUMP to follow
     * @return the next block to add to the trace, if one was found
     */
    private List<Stm> trace_cjump(CJUMP cj) {
        // the ideal case is that the false block hasn't been visited
        if (unvisited.containsKey(cj.iffalse)) {
            // in this case we can continue right away with the false block
            return unvisited.get(cj.iffalse);
        } else if (unvisited.containsKey(cj.iftrue)) { // if the false block wasn't found but the true block was
            // we swap the CJUMP's operator and labels so the true is now the false
            trace.set(trace.size() - 1, new CJUMP(CJUMP.notRel(cj.relop), cj.left, cj.right, cj.iffalse, cj.iftrue));
            return unvisited.get(cj.iftrue);
        } else { // if neither the true nor the false was found
            // we create a new label for the false fallthrough
            Label ff = new Label();
            // we set the false label of the CJUMP to the new label
            trace.set(trace.size() - 1, new CJUMP(cj.relop, cj.left, cj.right, cj.iftrue, ff));
            // we add the label and a jump to the actual false block to the trace
            trace.add(new LABEL(ff));
            trace.add(new JUMP(cj.iffalse));
            // there is no block to continue to
            return null;
        }
    }
}