package Tree;
/**
 * A temporary t.
 */
public class TEMP extends Tree.Exp {

    public Temp.Temp temp;

    public TEMP(Temp.Temp t) {
        temp = t;
    }

    public void accept(Tree.IntVisitor v, int d) {
        v.visit(this, d);
    }

}
