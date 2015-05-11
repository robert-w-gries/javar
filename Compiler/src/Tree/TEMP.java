package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * A temporary t.
 */
public class TEMP extends Tree.Exp {

    public Temp.Temp temp;

    public TEMP(Temp.Temp t) {
        temp = t;
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
