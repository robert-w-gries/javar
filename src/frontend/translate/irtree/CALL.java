package frontend.translate.irtree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements a procedure call, application of a function to an argument list.
 */
public class CALL extends Exp {

    public java.util.List<Exp> args;
    public Exp func;

    public CALL(Exp f, Exp... a) {
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
        Exp[] args = new Exp[kids.size()-1];
        for (int i = 1; i < kids.size(); i++) args[i-1] = kids.get(i);
        return new CALL(kids.get(0), args);
    }

    public void accept(IntVisitor v) {
        v.visit(this);
    }

}
