package Tree;
/**
 * Jump (transfer control) to address e.
 */
public class JUMP extends Tree.Stm {

    public Tree.Exp exp;
    public java.util.LinkedList<Temp.Label> targets;

    public JUMP(Tree.Exp e, java.util.LinkedList<Temp.Label> t) {
        exp = e;
        targets = t;
    }

    public JUMP(Temp.Label target) {
        targets = new java.util.LinkedList<Temp.Label>();
        targets.push(target);
        exp = new NAME(target);
    }

    public void accept(Tree.IntVisitor v, int d) {
        v.visit(this, d);
    }

}
