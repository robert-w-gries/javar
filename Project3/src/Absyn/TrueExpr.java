package Absyn;
/**
 * Boolean TRUE
 */
public class TrueExpr extends Absyn.Expr{
    public TrueExpr(){}

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
