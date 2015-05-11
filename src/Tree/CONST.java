package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * An integer constant.
 */
public class CONST extends Tree.Exp {

    public int value;

    public CONST(int v) {
        value = v;
    }

    @Override
    public List<Exp> kids() {
        return new LinkedList<Exp>();
    }

    @Override
    public Exp build(List<Exp> kids) {
        return this;
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
