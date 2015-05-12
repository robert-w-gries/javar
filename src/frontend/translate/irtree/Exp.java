package frontend.translate.irtree;

import frontend.typecheck.OBJECT;

import java.util.List;

/**
 * Abstract class that represents an expression in the Tree language.
 */
public abstract class Exp implements Hospitable {

    public OBJECT type;

    Exp() {

    }

    public abstract List<Exp> kids();

    public abstract Exp build(List<Exp> kids);

    public abstract void accept(IntVisitor v);

}
