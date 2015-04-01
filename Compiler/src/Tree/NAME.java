package Tree;
/**
 * The symbolic constant of the label.
 */
public class NAME extends Tree.Exp {

    public Temp.Label label;

    public NAME(Temp.Label l) {
        label = l;
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

}
