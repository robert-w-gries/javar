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
        v.visit(this);
    }

    @Override
    public Types.Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Translate.Exp accept(Translate.Translate t) { return t.visit(this);}
}