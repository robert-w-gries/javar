package Canon;

import Tree.CJUMP;
import Tree.JUMP;
import Tree.LABEL;
import Tree.Stm;

import java.util.LinkedList;
import java.util.List;

public class BasicBlocks {
    public List<List<Stm>> blocks;
    public Temp.Label done;

    /**
     * Splits up a list of statements into basic blocks and stores them internally
     * @param stms a list of statements to split
     */
    public BasicBlocks(List<Stm> stms) {
        blocks = new LinkedList<List<Stm>>();
        done = new Temp.Label();
        make_blocks(stms);
    }

    /**
     * Loops through each statement in a list and creates a series of basic blocks
     * @param stms a list of statements
     */
    private void make_blocks(List<Stm> stms) {
        // first block
        List<Stm> block = new LinkedList<Stm>();

        for (Stm stm : stms) {
            if (block.size() == 0) {
                // the current statement should be a label. if not, add one
                if (!(stm instanceof LABEL)) block.add(new LABEL(new Temp.Label()));

            } else if (stm instanceof LABEL) {
                // we've hit a label without hitting a jump first. add a jump and start a new block
                block.add(new JUMP(((LABEL)stm).label));

                block = start_new_block(block);
            }

            block.add(stm); // add the current statement to the current block

            if (stm instanceof JUMP || stm instanceof CJUMP) {
                // we just added the last statement of the current block, and we need a new one
                block = start_new_block(block);
            }
        }

        // add a jump to done at the very end
        blocks.get(blocks.size()-1).add(new JUMP(done));
    }

    /**
     * Saves away a block and returns a new one
     * @param current a block to save away
     * @return a new block
     */
    private List<Stm> start_new_block(List<Stm> current) {
        blocks.add(current);
        return new LinkedList<Stm>();
    }
}