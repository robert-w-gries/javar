package Tree;

import Types.OBJECT;

/**
 * Abstract class that represents an expression in the Tree language.
 */
public abstract class Exp implements Tree.Hospitable {

    public OBJECT type;

    public Exp() {

    }

    public abstract void accept(Tree.IntVisitor v);

}
