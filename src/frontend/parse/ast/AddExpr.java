package frontend.parse.ast;


import frontend.typecheck.Type;
import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * Addition Expressions.
 */
public class AddExpr extends BinOpExpr {

    public AddExpr(Expr e1, Expr e2){
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
