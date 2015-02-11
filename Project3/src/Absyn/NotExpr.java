package Absyn;
/**
 * Boolean (Logical) Not Expressions.
 */
public class NotExpr extends Absyn.Expr{

    public Absyn.Expr expr;

    public NotExpr(Absyn.Expr e1)
    {   expr = e1;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
