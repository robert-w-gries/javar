package frontend.translate.irtree;

import java.util.LinkedList;
import java.util.List;

/**
 * Sequence: statement 1 followed by statment 2.
 */
public class SEQ extends Stm {

    public Stm left;
    public Stm right;

    public SEQ(Stm l, Stm r) {
        left = l;
        right = r;
    }

    @Override
    public List<Exp> kids() {
        return new LinkedList<Exp>();
    }

    @Override
    public Stm build(List<Exp> kids) {
        return this;
    }

    public void accept(IntVisitor v) {
        v.visit(this);
    }

}
