package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

import java.util.LinkedList;

/**
 * Array Allocation.
 */
public class NewArrayExpr extends Expr {
    public Type type;
    public LinkedList<Expr> dimensions;

    public NewArrayExpr(Type type, java.util.LinkedList<Expr> dimensions) {
        this.type = type;
        this.dimensions = dimensions;
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
