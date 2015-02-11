package Absyn;
/**
 * Unary Negation Expressions.
 */
public class NegExpr extends Absyn.Expr{

    public Absyn.Expr expr;

    public NegExpr(Absyn.Expr e1)
    {   expr = e1;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
