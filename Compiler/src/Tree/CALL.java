package Tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    public List<Exp> kids() {
        List<Exp> exps = new LinkedList<Exp>();
        exps.add(func);
        exps.addAll(args);
        return exps;
    }

    @Override
    public Exp build(List<Exp> kids) {
        Tree.Exp[] args = new Tree.Exp[this.args.size()];
        for (int i = 0; i < this.args.size(); i++) args[i] = this.args.get(i);
        return new CALL(kids.get(0), args);
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
