package frontend.parse.ast;

/**
 * Expressions with two operands.
 */
public abstract class BinOpExpr extends Expr {

    public Expr leftExpr;
    public Expr rightExpr;

    BinOpExpr(Expr e1, Expr e2){
        leftExpr = e1;
        rightExpr = e2; 
    }

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Visitor v);

}
