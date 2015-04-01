package Tree;
/**
 * Implements a move instruction.
 */
public class MOVE extends Tree.Stm {

    public Tree.Exp dst;
    public Tree.Exp src;

    public MOVE(Tree.Exp d, Tree.Exp s) {
        dst = d;
        src = s;
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
