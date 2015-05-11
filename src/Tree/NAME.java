package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * The symbolic constant of the label.
 */
public class NAME extends Tree.Exp {

    public Temp.Label label;

    public NAME(Temp.Label l) {
        label = l;
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
