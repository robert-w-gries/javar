package Tree;
/**
 * An integer constant.
 */
public class CONST extends Tree.Exp {

    public int value;

    public CONST(int v) {
        value = v;
    }

    public void accept(Tree.IntVisitor v, int d) {
        v.visit(this, d);
    }

}
