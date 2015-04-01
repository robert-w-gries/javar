package Tree;
/**
 * Define constant value as current code address.
 */
public class LABEL extends Tree.Stm {

    public Temp.Label label;

    public LABEL(Temp.Label l) {
        label = l;
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
