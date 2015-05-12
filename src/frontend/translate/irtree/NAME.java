package frontend.translate.irtree;

import arch.Label;

import java.util.LinkedList;
import java.util.List;

/**
 * The symbolic constant of the label.
 */
public class NAME extends Exp {

    public Label label;

    public NAME(Label l) {
        label = l;
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
