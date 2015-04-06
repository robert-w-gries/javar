package Mips;

import Tree.CONST;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:16 PM
 */
public class InFrame extends Frame.Access {

    int offset;

    public InFrame(int x) {
        offset = x;
    }

    public Tree.Exp exp(Tree.Exp framePtr) {
        return new Tree.MEM(new Tree.BINOP(Tree.BINOP.Operation.PLUS, framePtr, new CONST(offset)));
    }

}
