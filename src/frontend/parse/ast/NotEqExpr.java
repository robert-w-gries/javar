package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

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
        v.visit(this);
    }

    @Override
    public frontend.typecheck.Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Exp accept(Translate t) { return t.visit(this);}
}
