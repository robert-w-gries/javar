package Tree;

import java.util.List;

/**
 * An abstract class that represents a statment in the Tree language.
 */
public abstract class Stm implements Tree.Hospitable {

    public Stm() {

    }

    public abstract List<Exp> kids();

    public abstract Stm build(List<Exp> kids);

    public abstract void accept(Tree.IntVisitor v);
}
