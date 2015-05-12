package frontend.translate.irtree;

import arch.Temp;

import java.util.LinkedList;
import java.util.List;

/**
 * A temporary t.
 */
public class TEMP extends Exp {

    public Temp temp;

    public TEMP(Temp t) {
        temp = t;
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
