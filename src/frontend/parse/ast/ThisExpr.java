package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * The "this" reference.
 */
public class ThisExpr extends Expr {
    public ThisExpr() {}

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

    public Exp accept(Translate t) { return t.visitThis();}
}
