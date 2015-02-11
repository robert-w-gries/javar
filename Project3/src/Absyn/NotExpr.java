package Absyn;
/**
 * Boolean (Logical) Not Expressions.
 */
public class NotExpr extends Expr{
    public Expr expr;

    public NotExpr(Expr e1){
        this.expr = e1;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        return; //TODO codavaj!!
    }

}
