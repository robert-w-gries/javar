package frontend.parse.ast;

import frontend.typecheck.Type;
import frontend.translate.Exp;
import frontend.translate.Translate;

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
    }

    @Override
    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Exp accept(Translate t) { return t.visit(this);}
}
