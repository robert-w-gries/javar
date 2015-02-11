package Absyn;
/**
 * Boolean FALSE.
 */
public class FalseExpr extends Absyn.Expr{

    public FalseExpr(){}

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
