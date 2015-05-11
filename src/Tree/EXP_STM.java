package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Evaluates the expression and discards the result.
 */
public class EXP_STM extends Tree.Stm {

    public Tree.Exp exp;

    public EXP_STM(Tree.Exp e) {
         exp = e;
    }

    @Override
    public List<Exp> kids() {
        List<Exp> exps = new LinkedList<Exp>();
        exps.add(exp);
        return exps;
    }

    @Override
    public Stm build(List<Exp> kids) {
        return new EXP_STM(kids.get(0));
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
