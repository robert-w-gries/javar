package frontend.translate.irtree;

import java.util.LinkedList;
import java.util.List;

/**
 * An integer constant.
 */
public class CONST extends Exp {

    public int value;

    public CONST(int v) {
        value = v;
    }

    @Override
    public List<Exp> kids() {
        return new LinkedList<>();
    }

    @Override
    public Exp build(List<Exp> kids) {
        return this;
    }

    public void accept(IntVisitor v) {
        v.visit(this);
    }

}
