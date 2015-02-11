package Absyn;
/**
 * Unary Negation Expressions.
 */
public class NegExpr extends Expr{
    public Expr expr;

    public NegExpr(Expr e1){
         this.expr = e1;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        return; //TODO codavaj!!
    }

}
