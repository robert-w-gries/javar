package Absyn;
/**
 * Array Allocation.
 */
public class NewArrayExpr extends Absyn.Expr{

    public Absyn.Type type;
    public java.util.LinkedList<Absyn.Expr> dimensions;

    public NewArrayExpr(Absyn.Type type, java.util.LinkedList<Absyn.Expr> dimensions)
    {   this.type = type;   for (int i = 0; i < dimensions.size(); i++) this.dimensions.set(i, dimensions.get(i)); }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }


}
