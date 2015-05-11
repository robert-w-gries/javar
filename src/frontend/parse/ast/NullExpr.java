package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * The "null" expression.
 */
public class NullExpr extends Expr {
    public NullExpr() {}

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

    public Exp accept(Translate t) { return t.visitNull();}
}
