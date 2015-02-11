package Absyn;
/**
 * The "this" reference.
 */
public class ThisExpr extends Absyn.Expr{

    public ThisExpr(){}

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
