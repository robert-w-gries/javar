package frontend.translate.irtree;

import arch.Label;

import java.util.LinkedList;
import java.util.List;

/**
 * Jump (transfer control) to address e.
 */
public class JUMP extends Stm {

    public Exp exp;
    public Label target;

    public JUMP(Exp e, Label t) {
        exp = e;
        target = t;
    }

    public JUMP(Label target) {
        this.target = target;
        exp = new NAME(target);
    }

    @Override
    public List<Exp> kids() {
        List<Exp> exps = new LinkedList<Exp>();
        exps.add(exp);
        return exps;
    }

    @Override
    public Stm build(List<Exp> kids) {
        return new JUMP(kids.get(0), target);
    }

    public void accept(IntVisitor v) {
        v.visit(this);
    }

}
