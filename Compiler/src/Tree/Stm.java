package Tree;
/**
 * An abstract class that represents a statment in the Tree language.
 */
public abstract class Stm implements Tree.Hospitable {

    public Stm() {

    }

    public abstract void accept(Tree.IntVisitor v);

}
