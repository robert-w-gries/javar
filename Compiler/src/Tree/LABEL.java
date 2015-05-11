package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Define constant value as current code address.
 */
public class LABEL extends Tree.Stm {

    public Temp.Label label;

    public LABEL(Temp.Label l) {
        label = l;
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
