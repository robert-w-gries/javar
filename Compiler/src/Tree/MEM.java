package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Contents of a word of memory starting at address e.
 */
public class MEM extends Tree.Exp {

    public Tree.Exp exp;

    public MEM(Tree.Exp e) {
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
        return new MEM(kids.get(0));
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
