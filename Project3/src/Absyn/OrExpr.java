package Absyn;
/**
 * Boolean (Logical) Or Expressions.
 */
public class OrExpr extends BinOpExpr{
    public OrExpr(Expr e1, Expr e2){
        super(e1, e2);
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        return; //TODO codavaj!!
    }

}
