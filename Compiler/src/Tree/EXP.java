package Tree;
/**
 * Evaluates the expression and discards the result.
 */
public class EXP extends Tree.Stm {

    public Tree.Exp exp;

    public EXP(Tree.Exp e) {
         exp = e;
    }

    public void accept(Tree.IntVisitor v, int d) {
        v.visit(this, d);
    }

}
