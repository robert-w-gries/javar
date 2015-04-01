package Tree;
/**
 * Abstract class that represents an expression in the Tree language.
 */
public abstract class Exp implements Tree.Hospitable {

    public Exp() {

    }

    public abstract void accept(Tree.IntVisitor v);

}
