package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Jump (transfer control) to address e.
 */
public class JUMP extends Tree.Stm {

    public Tree.Exp exp;
    public Temp.Label target;

    public JUMP(Tree.Exp e, Temp.Label t) {
        exp = e;
        target = t;
    }

    public JUMP(Temp.Label target) {
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

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
