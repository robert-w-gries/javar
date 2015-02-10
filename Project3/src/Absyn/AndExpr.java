package Absyn;

/**
 * Boolean (Logical) And Expressions.
 */
public class AndExpr extends Absyn.BinOpExpr{

    public Absyn.Expr leftExpr;
    public Absyn.Expr rightExpr;

    public AndExpr(Absyn.Expr e1, Absyn.Expr e2){
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
