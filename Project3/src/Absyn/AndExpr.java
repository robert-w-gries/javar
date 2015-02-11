package Absyn;

/**
 * Boolean (Logical) And Expressions.
 */
public class AndExpr extends BinOpExpr{

    public AndExpr(Expr e1, Expr e2){
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
