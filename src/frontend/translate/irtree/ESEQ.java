package frontend.translate.irtree;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements an expression sequence, statment is evaluated for side effects and the expression is evaluated as result.
 */
public class ESEQ extends Exp {

    public Exp exp;
    public Stm stm;

    public ESEQ(Stm s, Exp e) {
        stm = s;
        exp = e;
    }

    @Override
    public List<Exp> kids() {
        List<Exp> exps = new LinkedList<>();
        exps.add(exp);
        return exps;
    }

    @Override
    public Exp build(List<Exp> kids) {
        return new ESEQ(stm, kids.get(0));
    }

    public void accept(IntVisitor v) {
        v.visit(this);
    }

}
