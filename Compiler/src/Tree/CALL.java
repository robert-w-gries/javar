package Tree;
/**
 * Implements a procedure call, application of a function to an argument list.
 */
public class CALL extends Tree.Exp {

    public java.util.List<Tree.Exp> args;
    public Tree.Exp func;

    public CALL(Tree.Exp f, java.util.List<Tree.Exp> a) {
        func = f;
        args = a;
    }

    public void accept(Tree.IntVisitor v, int d) {
        v.visit(this, d);
    }

}
