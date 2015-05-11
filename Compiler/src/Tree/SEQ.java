package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Sequence: statement 1 followed by statment 2.
 */
public class SEQ extends Tree.Stm {

    public Tree.Stm left;
    public Tree.Stm right;

    public SEQ(Tree.Stm l, Tree.Stm r) {
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

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
