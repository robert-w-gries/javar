package Absyn;
/**
 * Object Allocation.
 */
public class NewObjectExpr extends Absyn.Expr{

    public Absyn.Type type;

    public NewObjectExpr(Absyn.Type type)
    {   this.type = type;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }


}
