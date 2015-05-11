package frontend.translate.irtree;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements a move instruction.
 */
public class MOVE extends Stm {

    public Exp dst;
    public Exp src;

    public MOVE(Exp d, Exp s) {
        dst = d;
        src = s;
    }

    @Override
    public List<Exp> kids() {
        List<Exp> exps = new LinkedList<Exp>();
        if (dst instanceof MEM) exps.add(((MEM)dst).exp);
        exps.add(src);
        return exps;
    }

    @Override
    public Stm build(List<Exp> kids) {
        if (dst instanceof MEM) return new MOVE(new MEM(kids.get(0)), kids.get(1));
        return new MOVE(dst, kids.get(0));
    }

    public void accept(IntVisitor v) {
        v.visit(this);
    }

}
