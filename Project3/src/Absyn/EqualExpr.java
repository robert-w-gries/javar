package Absyn;
/**
 * Boolean (Logical) Equality Expressions.
 */
public class EqualExpr extends Absyn.BinOpExpr{

    public Absyn.Expr leftExpr;
    public Absyn.Expr rightExpr;

    public EqualExpr(Absyn.Expr e1, Absyn.Expr e2)
    {   leftExpr = e1;   rightExpr = e2;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
