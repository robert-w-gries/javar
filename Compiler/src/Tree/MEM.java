package Tree;
/**
 * Contents of a word of memory starting at address e.
 */
public class MEM extends Tree.Exp {

    public Tree.Exp exp;

    public MEM(Tree.Exp e) {
        exp = e;
    }

    public void accept(Tree.IntVisitor v, int d) {
        v.visit(this, d);
    }

}
