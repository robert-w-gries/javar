package Absyn;

/**
 * Expressions with two operands.
 */
public abstract class BinOpExpr extends Absyn.Expr{

    public Absyn.Expr leftExpr;
    public Absyn.Expr rightExpr;

    public BinOpExpr(Absyn.Expr e1, Absyn.Expr e2){
        leftExpr = e1;
        rightExpr = e2; 
    }

}
