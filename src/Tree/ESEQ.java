package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements an expression sequence, statment is evaluated for side effects and the expression is evaluated as result.
 */
public class ESEQ extends Tree.Exp {

    public Tree.Exp exp;
    public Tree.Stm stm;

    public ESEQ(Tree.Stm s, Tree.Exp e) {
        stm = s;
        exp = e;
    }

    @Override
    public List<Exp> kids() {
        List<Exp> exps = new LinkedList<Exp>();
        exps.add(exp);
        return exps;
    }

    @Override
    public Exp build(List<Exp> kids) {
        return new ESEQ(stm, kids.get(0));
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
