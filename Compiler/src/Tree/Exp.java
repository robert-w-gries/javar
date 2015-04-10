package Tree;

import Types.OBJECT;

import java.util.List;

/**
 * Abstract class that represents an expression in the Tree language.
 */
public abstract class Exp implements Tree.Hospitable {

    public OBJECT type;

    public Exp() {

    }

    public abstract List<Exp> kids();

    public abstract Exp build(List<Exp> kids);

    public abstract void accept(Tree.IntVisitor v);

}
