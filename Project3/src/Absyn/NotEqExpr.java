package Absyn;
/**
 * Boolean (Logical) NOT Expressions.
 */
public class NotEqExpr extends BinOpExpr{
    public NotEqExpr(Expr e1, Expr e2){
        super(e1, e2);
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        return; //TODO codavaj!!
    }

}
