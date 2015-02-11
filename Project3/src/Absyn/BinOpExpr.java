package Absyn;

/**
 * Expressions with two operands.
 */
public abstract class BinOpExpr extends Expr {

    public Expr leftExpr;
    public Expr rightExpr;

    public BinOpExpr(Expr e1, Expr e2){
        leftExpr = e1;
        rightExpr = e2; 
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
