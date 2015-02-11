package Absyn;
/**
 * Boolean (Logical) Equality Expressions.
 */
public class EqualExpr extends BinOpExpr{
    public EqualExpr(Expr e1, Expr e2){
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
