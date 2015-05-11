package frontend.translate.irtree;

import arch.Label;

import java.util.LinkedList;
import java.util.List;

/**
 * Define constant value as current code address.
 */
public class LABEL extends Stm {

    public Label label;

    public LABEL(Label l) {
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

    public void accept(IntVisitor v) {
        v.visit(this);
    }

}
