package Absyn;
/**
 * Subtraction Expressions.
 */
public class SubExpr extends BinOpExpr{
    public SubExpr(Expr e1, Expr e2){
        super(e1, e2);
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
        return;
    }

}
