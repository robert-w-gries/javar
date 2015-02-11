package Absyn;
/**
 * The "null" expression.
 */
public class NullExpr extends Absyn.Expr{

    public NullExpr(){}

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
