package Tree;

import java.util.Arrays;

/**
 * Implements a procedure call, application of a function to an argument list.
 */
public class CALL extends Tree.Exp {

    public java.util.List<Tree.Exp> args;
    public Tree.Exp func;

    public CALL(Tree.Exp f, Tree.Exp... a) {
        func = f;
        args = Arrays.asList(a);
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
